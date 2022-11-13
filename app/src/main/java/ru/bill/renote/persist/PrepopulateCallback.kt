package ru.bill.renote.persist

import android.content.ContentValues
import androidx.room.OnConflictStrategy
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import ru.bill.renote.common.extensions.isEven
import ru.bill.renote.persist.entities.Category
import ru.bill.renote.persist.entities.Note
import ru.bill.renote.persist.entities.NoteCategoryCrossRef

class PrepopulateCallback : RoomDatabase.Callback() {

  private val randomTitles = arrayOf(
    "The most random title ever seen",
    "The most random title ever seen #2",
    "My precious application",
    "ReNote Introduction",
    "Films to watch",
    "Food to cook",
    "Notes to remember before a Math exam comes"
  )
  private val randomBodies = arrayOf(
    "The most random body ever seen",
    "My application is precious!Very long and very informative. I hope you are having a nice time here, in ReNote.\nAnd it's multiline note!!",
    "1.TENNET\n2.NETTEN\n3.Yes\n4.No"
  )
  private val randomCategories = arrayOf(
    "Todo",
    "To-repeat"
  )

  override fun onCreate(db: SupportSQLiteDatabase) {
    super.onCreate(db)

    for (i in 0..4) {
      val noteToInsert = Note.createNew(randomTitles.random(), randomBodies.random())
      db.insertOnReplace(Note.TABLE_NAME, noteToInsert.toContentValues())

      val categoryToInsert = Category.createNew(randomCategories.random())
      db.insertOnReplace(Category.TABLE_NAME, categoryToInsert.toContentValues())

      if (i.isEven()) {
        val noteCategoryJoin = NoteCategoryCrossRef(noteToInsert.id, categoryToInsert.id)
        db.insertOnReplace(NoteCategoryCrossRef.TABLE_NAME, noteCategoryJoin.toContentValues())
      }
    }

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

  private fun NoteCategoryCrossRef.toContentValues(): ContentValues {
    return ContentValues(2).apply {
      put(NoteCategoryCrossRef.COLUMN_REF_NOTE_ID, noteId)
      put(NoteCategoryCrossRef.COLUMN_REF_CATEGORY_ID, categoryId)
    }
  }

  private fun SupportSQLiteDatabase.insertOnReplace(tableName: String, cv: ContentValues){
    this.insert(tableName, OnConflictStrategy.REPLACE, cv)
  }
}
