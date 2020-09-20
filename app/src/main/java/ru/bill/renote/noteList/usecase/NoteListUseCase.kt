package ru.bill.renote.noteList.usecase

import io.reactivex.Completable
import io.reactivex.Single
import ru.bill.renote.persist.dao.NoteCategoryDao
import ru.bill.renote.persist.dao.NotesDao
import ru.bill.renote.persist.entities.Note
import ru.bill.renote.persist.junctions.NoteWithCategories
import javax.inject.Inject

class NoteListUseCase @Inject constructor(
    private val notesDao: NotesDao,
    private val noteCategoryDao: NoteCategoryDao
) {

  fun getAllNotes(): Single<List<NoteWithCategories>> = noteCategoryDao.loadAllNotesWithCategories()

  fun deleteNote(noteToDelete: Note): Completable {
    return notesDao
      .delete(noteToDelete)
      .andThen(noteCategoryDao.deleteByNoteId(noteToDelete.id))
  }
}
