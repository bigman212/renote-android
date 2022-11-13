package com.bill.renote.noteList

import androidx.lifecycle.MutableLiveData
import com.bill.renote.base.BaseViewModel
import com.bill.renote.base.Event
import com.bill.renote.base.ShowSnackbarEvent
import com.bill.renote.common.extensions.delegate
import ru.bill.renote.common.extensions.scheduleIoToUi
import com.bill.renote.data.persist.SchedulersProvider
import timber.log.Timber
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val noteListUseCase: NoteListUseCase,
    private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

    sealed class ScreenState {
        data class Content(val data: List<NoteWithCategories>) : ScreenState()
        object Loading : ScreenState()

        object Empty : ScreenState()

        companion object {
            fun initial(): ScreenState = Content(listOf())
        }
    }

    data class NoteIsDeletedEvent(val deleteNoteItem: NoteWithCategories) : Event

    val viewState: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.initial())
    private var state: ScreenState by viewState.delegate()

    fun fetchAllNotes() {
        noteListUseCase.getAllNotes()
            .doOnSubscribe { state = ScreenState.Loading }
            .scheduleIoToUi(schedulersProvider)
            .subscribe(
                { notes -> state = if (notes.isEmpty()) ScreenState.Empty else ScreenState.Content(notes) },
                { error ->
                    Timber.e(error)
                    sendErrorEvent(error)
                }
            ).disposeOnCleared()
    }

    fun deleteNote(noteWithCategories: NoteWithCategories) {
        noteListUseCase.deleteNote(noteWithCategories.note)
            .doOnSubscribe { state = ScreenState.Loading }
            .scheduleIoToUi(schedulersProvider)
            .subscribe(
                {
                    events.push(NoteIsDeletedEvent(noteWithCategories))
                    events.push(ShowSnackbarEvent("Note deleted successfully"))
                },
                { error ->
                    Timber.e(error)
                    sendErrorEvent(error)
                }
            ).disposeOnCleared()
    }
}
