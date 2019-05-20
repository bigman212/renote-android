package ru.bill.renote

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun all(): LiveData<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note)

    @Query("SELECT * FROM notes WHERE id=:id")
    fun noteById(id: Long): LiveData<Note>

    @Query("DELETE FROM notes WHERE id=:id")
    fun delete(id: Long)
}