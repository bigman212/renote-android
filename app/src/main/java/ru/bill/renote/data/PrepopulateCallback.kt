package ru.bill.renote.data

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.bill.renote.data.entities.Category
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.NoteCategoryJoin

class PrepopulateCallback : RoomDatabase.Callback() {

  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)

    for (i in 0..3) {
      val noteToInsert = Note.createNew("Тайтл $i", "Боди $i")
      db.insert(Note.TABLE_NAME, OnConflictStrategy.REPLACE, noteToInsert.toContentValues())

      val categoryToInsert = Category.createNew("Категория $i")
      db.insert(Category.TABLE_NAME, OnConflictStrategy.REPLACE, categoryToInsert.toContentValues())
    }

    var noteCategoryJoin = NoteCategoryJoin(1, 1)
    db.insert(NoteCategoryJoin.TABLE_NAME, OnConflictStrategy.REPLACE, noteCategoryJoin.toContentValues())

    noteCategoryJoin = NoteCategoryJoin(2, 1)
    db.insert(NoteCategoryJoin.TABLE_NAME, OnConflictStrategy.REPLACE, noteCategoryJoin.toContentValues())

    noteCategoryJoin = NoteCategoryJoin(2, 2)
    db.insert(NoteCategoryJoin.TABLE_NAME, OnConflictStrategy.REPLACE, noteCategoryJoin.toContentValues())

    noteCategoryJoin = NoteCategoryJoin(2, 3)
    db.insert(NoteCategoryJoin.TABLE_NAME, OnConflictStrategy.REPLACE, noteCategoryJoin.toContentValues())


//    noteToInsert = Note.createNew("Тайтл 2", "Боди 2")
//    db.insert(Note.TABLE_NAME, OnConflictStrategy.REPLACE, noteToInsert.toContentValues())
//
//    noteToInsert = Note.createNew("Тайтл 3", "Боди 3")
//    db.insert(Note.TABLE_NAME, OnConflictStrategy.REPLACE, noteToInsert.toContentValues())
  }

  private fun Note.toContentValues(): ContentValues {
    return ContentValues(4).apply {
      put(Note.COLUMN_ID, id)
      put(Note.COLUMN_TITLE, title)
      put(Note.COLUMN_BODY, body)
      putNull(Note.COLUMN_SOURCE_LINK)
    }
  }

  private fun Category.toContentValues(): ContentValues {
    return ContentValues(2).apply {
      put(Category.COLUMN_ID, id)
      put(Category.COLUMN_NAME, name)
    }
  }

  private fun NoteCategoryJoin.toContentValues(): ContentValues {
    return ContentValues(2).apply {
      put(NoteCategoryJoin.COLUMN_REF_NOTE_ID, noteId)
      put(NoteCategoryJoin.COLUMN_REF_CATEGORY_ID, categoryId)
    }
  }
}
