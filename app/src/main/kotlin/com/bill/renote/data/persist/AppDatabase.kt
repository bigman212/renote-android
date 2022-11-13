package com.bill.renote.data.persist

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bill.renote.data.persist.dao.CategoriesDao
import com.bill.renote.data.persist.dao.NoteCategoryDao
import com.bill.renote.data.persist.dao.NotesDao
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteEntity

@Database(
    exportSchema = false,
    version = 2,
    entities = [
        NoteEntity::class,
        CategoryEntity::class,
        NoteCategoryCrossRef::class
    ],
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DB_NAME = "renote.db"
    }

    abstract val notesDao: NotesDao
    abstract val categoriesDao: CategoriesDao
    abstract val noteCategoryDao: NoteCategoryDao
}
