package ru.bill.renote.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
        .addCallback(object: Callback(){
          override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            val populate = populate()
            instance(context).categoriesDao()
              .saveAll(populate)
              .ioSubscribe()
              .uiObserve()
              .subscribe()
          }
        })
        .fallbackToDestructiveMigration()
        .build()

    private fun populate(): ArrayList<Category> {
      val c = Category("Music")
      val c2 = Category("Hobby")
      val c3 = Category("Programming")
      return arrayListOf(c, c2, c3)
    }
  }


}