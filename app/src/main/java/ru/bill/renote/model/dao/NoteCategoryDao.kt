package ru.bill.renote.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Single
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin

@Dao
interface NoteCategoryDao {
  @Insert
  fun insert(playlistSongJoin: NoteCategoryJoin)

  @Query(
    """
           SELECT * FROM notes
           INNER JOIN note_category_join
           ON notes.id=note_category_join.noteId
           WHERE note_category_join.categoryId=:categoryId
           """
  )
  fun notesForCategory(categoryId: Long): Single<List<Note>>

  @Query(
    """
           SELECT * FROM categories
           INNER JOIN note_category_join
           ON categories.id=note_category_join.categoryId
           WHERE note_category_join.noteId=:noteId
           """
  )
  fun categoriesForNote(noteId: Long): Single<List<Category>>

}