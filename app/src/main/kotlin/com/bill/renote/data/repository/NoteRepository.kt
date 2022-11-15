package com.bill.renote.data.repository

import com.bill.renote.data.persist.dao.NoteCategoryDao
import com.bill.renote.data.persist.dao.NotesDao
import com.bill.renote.data.persist.di.RoomTransactionRunner
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteEntity
import com.bill.renote.data.persist.junctions.NoteWithCategories

class NoteRepository(
    private val notesDao: NotesDao,
    private val noteCategoryDao: NoteCategoryDao,
    private val roomTransactionRunner: RoomTransactionRunner
) {
    private var noteToDelete: NoteWithCategories? = null

    suspend fun getAllNotes(): List<NoteEntity> = notesDao.loadAll()

    suspend fun getAllNotesWithCategories(): List<NoteWithCategories> = noteCategoryDao.loadAllNotesWithCategories()

    suspend fun createNote(noteName: String, noteBody: String): NoteEntity {
        val entity = NoteEntity.createNew(
            title = noteName,
            body = noteBody
        )
        notesDao.save(entity)
        return entity
    }

    suspend fun startDeleteNoteById(noteId: String) {
        this.noteToDelete = noteCategoryDao.loadNoteWithCategoriesById(noteId)
        roomTransactionRunner.runAsTransaction {
            notesDao.delete(noteId)
            noteCategoryDao.deleteByNoteId(noteId)
        }
    }

    suspend fun revertDeleteNote() {
        noteToDelete?.let { note ->
            roomTransactionRunner.runAsTransaction {
                notesDao.save(note.note)
                val crossRefs = note.noteCategories.map {
                    NoteCategoryCrossRef(
                        noteId = note.note.id,
                        categoryId = it.id
                    )
                }
                noteCategoryDao.insertAll(crossRefs)
            }
        }
    }

    fun finishDeleteNoteById() {
        noteToDelete = null
    }
}