package com.bill.renote.noteList

import androidx.lifecycle.MutableLiveData
import com.bill.renote.R
import com.bill.renote.base.BaseViewModel
import com.bill.renote.base.Event
import com.bill.renote.base.ShowSnackbarEvent
import com.bill.renote.base.SnackbarButton
import com.bill.renote.common.extensions.delegate
import com.bill.renote.data.persist.junctions.CategoryWithNotes
import com.bill.renote.data.persist.junctions.NoteWithCategories
import com.bill.renote.data.repository.CategoriesRepository
import com.bill.renote.data.repository.NoteRepository
import com.bill.renote.noteList.adapter.NoteListItemModelData
import timber.log.Timber
import kotlinx.coroutines.launch

class NoteListViewModel(
    private val noteRepository: NoteRepository,
    private val categoriesRepository: CategoriesRepository
) : BaseViewModel() {

    sealed interface ScreenState {
        data class NotesAndCategories(
            val notes: List<NoteListItemModelData>,
            val categories: List<CategoryWithNotes>
        ) : ScreenState

        data class OnlyNotes(val notes: List<NoteListItemModelData>): ScreenState
        data class OnlyCategories(val categories: List<CategoryWithNotes>) : ScreenState

        object Loading : ScreenState
        object Empty : ScreenState

        companion object {
            fun initial(): ScreenState = Loading
        }
    }

    object NoteIsDeletedEvent : Event

    val viewState: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.initial())
    private var state: ScreenState by viewState.delegate()

    fun fetchAllNotes() {
        viewState.postValue(ScreenState.Loading)
        state = ScreenState.Loading
        launch {
            state = fetchNotesAndGetState()
        }
    }

    private suspend fun fetchNotesAndGetState(): ScreenState {
        return try {
            val notes = noteRepository.getAllNotesWithCategories().map(::NoteListItemModelData)
            val categories = categoriesRepository.getAllCategoriesWithNotes()
            // for better readability
            @Suppress("KotlinConstantConditions")
            when {
                categories.isNotEmpty() && notes.isNotEmpty() -> ScreenState.NotesAndCategories(notes, categories)
                categories.isNotEmpty() && notes.isEmpty() -> ScreenState.OnlyCategories(categories)
                categories.isEmpty() && notes.isNotEmpty() -> ScreenState.OnlyNotes(notes)
                else -> ScreenState.Empty
            }
        } catch (e: Throwable) {
            Timber.e(e)
            ScreenState.Empty
        }
    }

    fun startDeleteNote(noteWithCategories: NoteWithCategories) {
        launch {
            try {
                noteRepository.startDeleteNoteById(noteWithCategories.note.id)
                events.push(NoteIsDeletedEvent)
                events.push(
                    ShowSnackbarEvent(
                        message = "Note deleted successfully",
                        snackbarButton = SnackbarButton(
                            R.string.note_list_delete_revert,
                            onActionClicked = { revertDeleteNote() }
                        ),
                        onSnackbarDismissed = {
                            noteRepository.finishDeleteNoteById()
                        }
                    )
                )
            } catch (e: Throwable) {
                events.push(ShowSnackbarEvent(message = "Can't delete note for unknown reasons"))
                Timber.e(e)
            }
        }
    }

    private fun revertDeleteNote() {
        launch {
            try {
                noteRepository.revertDeleteNote()
            } catch (e: Throwable) {
                Timber.e(e)
            }
        }
    }
}
