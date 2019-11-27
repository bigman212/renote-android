package ru.bill.renote.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import io.reactivex.Completable
import ru.bill.renote.extensions.ioSubscribe
import ru.bill.renote.extensions.uiObserve
import ru.bill.renote.model.dao.CategoriesDao
import ru.bill.renote.model.dao.NoteCategoryDao
import ru.bill.renote.model.dao.NotesDao
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin


private const val DB_NAME = "notes.db"

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
        .addCallback(object : Callback() {
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val dbRoom = instance(context)
            Completable.concatArray(
              dbRoom.categoriesDao().saveAll(populateCategories()),
              dbRoom.notesDao().saveAll(populateNotes())
            )
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
      Category("ReNoteing")
    )

    private fun populateNotes(): List<Note> = listOf(
      Note(
        "Science of Smile",
        "Just smile, smile, smile! " +
            "IS IT JUST ME OR IS IT GETTING CRAZIER OUT THERE? " +
            "I USED TO THINK MY LIFE WAS A TRAGEDY... " +
            "ALL I HAVE ARE NEGATIVE THOUGHTS. " +
            "YOU WOULDN'T GET IT. " +
            "YOU GET WHAT YOU F**KING DESERVE!"
      ),
      Note("ReNoting is hot!", "Keep moving forward!")
    )
  }


}