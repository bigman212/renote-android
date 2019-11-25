package ru.bill.renote.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin

@Dao
interface NoteCategoryDao {
  @Insert
  fun insert(playlistSongJoin: NoteCategoryJoin): Maybe<Long>

  @Query(
    """
           SELECT DISTINCT id, title, body, source_link FROM notes
           INNER JOIN note_category_join
           ON notes.id=note_category_join.noteId
           WHERE note_category_join.categoryId=:categoryId
           """
  )
  // using observable to return [] instead of empty maybe
  fun notesForCategory(categoryId: Long): Observable<List<Note>>

  @Query(
    """
           SELECT DISTINCT id, title, body, source_link FROM notes
           INNER JOIN note_category_join
           ON notes.id=note_category_join.noteId
           WHERE note_category_join.categoryId IN (:categoriesId)
           """
  )
  fun notesForCategories(categoriesId: List<Long>): Observable<List<Note>>

  @Query(
    """
           SELECT DISTINCT id, name FROM categories
           INNER JOIN note_category_join
           ON categories.id=note_category_join.categoryId
           WHERE note_category_join.noteId=:noteId
           """
  )
  fun categoriesForNote(noteId: Long): Observable<List<Category>>

}