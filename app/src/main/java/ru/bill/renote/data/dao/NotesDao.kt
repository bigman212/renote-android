package ru.bill.renote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import ru.bill.renote.data.entities.Note

@Dao
interface NotesDao {
    @Query("SELECT * FROM notes")
    fun all(): Observable<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note) : Maybe<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(notes: List<Note>) : Maybe<List<Long>>

    @Query("SELECT * FROM notes WHERE id=:id LIMIT 1")
    fun noteById(id: Long): Maybe<Note>

    @Query("DELETE FROM notes WHERE id=:id")
    fun delete(id: Long) : Completable
}
