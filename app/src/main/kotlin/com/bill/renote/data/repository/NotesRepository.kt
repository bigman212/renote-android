package com.bill.renote.data.repository

import com.bill.renote.data.persist.dao.NoteCategoryDao
import com.bill.renote.data.persist.dao.NotesDao
import com.bill.renote.data.persist.di.RoomTransactionRunner
import com.bill.renote.data.persist.entities.NoteEntity
import com.bill.renote.data.persist.junctions.NoteWithCategories

class NotesRepository(
    private val notesDao: NotesDao,
    private val noteCategoryDao: NoteCategoryDao,
    private val roomTransactionRunner: RoomTransactionRunner
) {
    suspend fun getAllNotes(): List<NoteEntity> = notesDao.loadAll()

    suspend fun getAllNotesWithCategories(): List<NoteWithCategories> = noteCategoryDao.loadAllNotesWithCategories()

    suspend fun deleteNoteById(noteId: String) {
        roomTransactionRunner.runAsTransaction {
            notesDao.delete(noteId)
            noteCategoryDao.deleteByNoteId(noteId)
        }
    }
}