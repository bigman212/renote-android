package ru.bill.renote.persist.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.bill.renote.persist.entities.Category
import ru.bill.renote.persist.entities.Note
import ru.bill.renote.persist.entities.NoteCategoryCrossRef
import ru.bill.renote.persist.junctions.CategoryWithNotes
import ru.bill.renote.persist.junctions.NoteWithCategories

@Dao
interface NoteCategoryDao {
  @Transaction
  @Query("SELECT * from ${Note.TABLE_NAME}")
  fun loadAllNotesWithCategories(): Single<List<NoteWithCategories>>

  @Transaction
  @Query("SELECT * from ${Category.TABLE_NAME}")
  fun loadAllCategoriesWithNotes(): Single<List<CategoryWithNotes>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(noteCategoryJoin: NoteCategoryCrossRef): Completable

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(noteCategoryJoins: List<NoteCategoryCrossRef>): Completable

  @Query("DELETE FROM ${NoteCategoryCrossRef.TABLE_NAME} WHERE ref_note_id = :noteId")
  fun deleteByNoteId(noteId: String): Completable
}
