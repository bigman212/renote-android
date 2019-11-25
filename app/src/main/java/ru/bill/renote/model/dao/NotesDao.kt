package ru.bill.renote.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.bill.renote.model.entities.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun all(): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note) : Maybe<Long>

    @Query("SELECT * FROM notes WHERE id=:id LIMIT 1")
    fun noteById(id: Long): Maybe<Note>

    @Query("DELETE FROM notes WHERE id=:id")
    fun delete(id: Long)
}