package ru.bill.renote.model.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import ru.bill.renote.model.entities.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun all(): Maybe<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note)

    @Query("SELECT * FROM notes WHERE id=:id LIMIT 1")
    fun noteById(id: Long): Maybe<Note>

    @Query("DELETE FROM notes WHERE id=:id")
    fun delete(id: Long)
}