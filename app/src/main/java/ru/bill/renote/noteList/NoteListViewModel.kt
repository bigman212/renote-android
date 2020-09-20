package ru.bill.renote.noteList

import androidx.lifecycle.MutableLiveData
import ru.bill.renote.base.BaseViewModel
import ru.bill.renote.base.Event
import ru.bill.renote.base.MessageEvent
import ru.bill.renote.common.extensions.delegate
import ru.bill.renote.common.extensions.scheduleIoToUi
import ru.bill.renote.common.rx.SchedulersProvider
import ru.bill.renote.noteList.usecase.NoteListUseCase
import ru.bill.renote.persist.junctions.NoteWithCategories
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
          events.append(NoteIsDeletedEvent(noteWithCategories))
          events.append(MessageEvent("Note deleted successfully"))
        },
        { error ->
          Timber.e(error)
          sendErrorEvent(error)
        }
      ).disposeOnCleared()
  }
}
