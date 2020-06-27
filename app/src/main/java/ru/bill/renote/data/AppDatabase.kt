package ru.bill.renote.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Maybe
import io.reactivex.functions.BiFunction
import ru.bill.renote.data.dao.CategoriesDao
import ru.bill.renote.data.dao.NoteCategoryDao
import ru.bill.renote.data.dao.NotesDao
import ru.bill.renote.data.entities.Category
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.NoteCategoryJoin
import ru.bill.renote.extensions.ioSubscribe
import ru.bill.renote.extensions.uiObserve


private const val DB_NAME = "notes"
@Database(entities = [Note::class, Category::class, NoteCategoryJoin::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

  abstract fun notesDao(): NotesDao
  abstract fun categoriesDao(): CategoriesDao
  abstract fun noteCategoryDao(): NoteCategoryDao

  companion object {
    @Volatile
    private var mInstance: AppDatabase? = null

    fun instance(context: Context): AppDatabase {
      return mInstance ?: synchronized(this) {
        buildDatabase(context)
          .also { mInstance = it }
      }
    }

    private fun buildDatabase(context: Context) =
      Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DB_NAME)
        .setJournalMode(JournalMode.WRITE_AHEAD_LOGGING)
        .addCallback(object : Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dbRoom = instance(context)
            Maybe.zip(
              dbRoom.categoriesDao().saveAll(populateCategories()),
              dbRoom.notesDao().saveAll(populateNotes()), BiFunction { t1: List<Long>, t2: List<Long> ->
                  Log.d("pre-populating:", t1.toString())
                  Log.d("pre-populating:", t2.toString())
                  listOf(NoteCategoryJoin(t1[0], t2[0]), NoteCategoryJoin(t1[0], t2[1]))
              })
              .ioSubscribe()
              .flatMap { dbRoom.noteCategoryDao().insertAll(it) }
              .ioSubscribe()
              .uiObserve()
              .subscribe()
          }
        })
        .fallbackToDestructiveMigration()
        .build()

    private fun populateCategories(): List<Category> = listOf(
      Category("Music"),
      Category("Hobby"),
      Category("Programming"),
      Category("ReNoting")
    )

    private fun populateNotes(): List<Note> = listOf(
      Note(
        "Science of Smile",
        "1) Just smile, smile, smile!\n" +
            "2) IS IT JUST ME OR IS IT GETTING CRAZIER OUT THERE?\n" +
            "3) I USED TO THINK MY LIFE WAS A TRAGEDY...\n" +
            "4) ALL I HAVE ARE NEGATIVE THOUGHTS.\n" +
            "5) YOU WOULDN'T GET IT.\n" +
            "6) YOU GET WHAT YOU F**KING DESERVE!"
      ),
      Note("ReNoting is hot!", "Keep moving forward!")
    )
  }
}
