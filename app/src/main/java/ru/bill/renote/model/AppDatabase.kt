package ru.bill.renote.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.bill.renote.model.dao.CategoriesDao
import ru.bill.renote.model.dao.NotesDao
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note

private const val DB_NAME = "notes.db"

@Database(entities = [Note::class, Category::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun notesDao(): NotesDao
    abstract fun categoriesDao(): CategoriesDao

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
            Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java,
                DB_NAME
            )
                .fallbackToDestructiveMigration()
                .build()
    }


}