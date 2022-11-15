package com.bill.renote.data.persist.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef.Companion.COLUMN_REF_NOTE_ID
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef.Companion.TABLE_NAME
import com.bill.renote.data.persist.entities.NoteEntity
import com.bill.renote.data.persist.junctions.CategoryWithNotes
import com.bill.renote.data.persist.junctions.NoteWithCategories

@Dao
interface NoteCategoryDao {
    @Transaction
    @Query("SELECT * from ${NoteEntity.TABLE_NAME}")
    suspend fun loadAllNotesWithCategories(): List<NoteWithCategories>

    @Transaction
    @Query("SELECT * from ${NoteEntity.TABLE_NAME} WHERE ${NoteEntity.COLUMN_ID} = :noteId ")
    suspend fun loadNoteWithCategoriesById(noteId: String): NoteWithCategories?

    @Transaction
    @Query("SELECT * from ${CategoryEntity.TABLE_NAME}")
    suspend fun loadAllCategoriesWithNotes(): List<CategoryWithNotes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(noteCategoryJoin: NoteCategoryCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(noteCategoryJoins: List<NoteCategoryCrossRef>)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_REF_NOTE_ID = :noteId")
    suspend fun deleteByNoteId(noteId: String)
}
