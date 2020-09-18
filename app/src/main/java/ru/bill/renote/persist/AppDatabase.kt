package ru.bill.renote.persist

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.bill.renote.persist.dao.CategoriesDao
import ru.bill.renote.persist.dao.NoteCategoryDao
import ru.bill.renote.persist.dao.NotesDao
import ru.bill.renote.persist.entities.Category
import ru.bill.renote.persist.entities.Note
import ru.bill.renote.persist.entities.NoteCategoryCrossRef


@Database(entities = [Note::class, Category::class, NoteCategoryCrossRef::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {
  companion object {
    const val DB_NAME = "app.db"
  }

  abstract fun notesDao(): NotesDao
  abstract fun categoriesDao(): CategoriesDao
  abstract fun noteCategoryDao(): NoteCategoryDao

//    private fun populateNotes(): List<Note> = listOf(
//      Note(
//        "Science of Smile",
//        "1) Just smile, smile, smile!\n" +
//            "2) IS IT JUST ME OR IS IT GETTING CRAZIER OUT THERE?\n" +
//            "3) I USED TO THINK MY LIFE WAS A TRAGEDY...\n" +
//            "4) ALL I HAVE ARE NEGATIVE THOUGHTS.\n" +
//            "5) YOU WOULDN'T GET IT.\n" +
//            "6) YOU GET WHAT YOU F**KING DESERVE!"
//      ),
//      Note("ReNoting is hot!", "Keep moving forward!")
//    )
}
