package ru.bill.renote.data.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.Note.Companion.COLUMN_ID
import ru.bill.renote.data.entities.Note.Companion.TABLE_NAME

@Dao
interface NotesDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun loadAll(): Single<List<Note>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(note: Note) : Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAll(notes: List<Note>) : Completable

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID LIKE :noteId LIMIT 1")
    fun loadById(noteId: Long): Maybe<Note>

    @Delete
    fun delete(note: Note) : Completable
}
