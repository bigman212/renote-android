package com.bill.renote.data.persist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bill.renote.data.persist.entities.NoteEntity
import com.bill.renote.data.persist.entities.NoteEntity.Companion.COLUMN_ID
import com.bill.renote.data.persist.entities.NoteEntity.Companion.TABLE_NAME

@Dao
interface NotesDao {
    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun loadAll(): List<NoteEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(note: NoteEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(notes: List<NoteEntity>)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID LIKE :noteId LIMIT 1")
    suspend fun loadById(noteId: String): NoteEntity

    @Delete
    suspend fun delete(noteEntity: NoteEntity)

    @Query("DELETE FROM $TABLE_NAME WHERE $COLUMN_ID = :noteId")
    suspend fun delete(noteId: String)
}
