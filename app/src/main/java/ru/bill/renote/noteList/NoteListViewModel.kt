package ru.bill.renote.noteList

import androidx.lifecycle.MutableLiveData
import ru.bill.renote.base.BaseViewModel
import ru.bill.renote.common.rx.SchedulersProvider
import ru.bill.renote.common.rx.scheduleIoToUi
import ru.bill.renote.data.dao.NotesDao
import ru.bill.renote.data.entities.Note
import ru.bill.renote.util.delegate
import timber.log.Timber
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val notesDao: NotesDao, private val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

  sealed class ScreenState {
    data class Content(val data: List<Note>) : ScreenState()
    object Loading : ScreenState()

    object Empty : ScreenState()

    companion object {
      fun initial(): ScreenState = Content(listOf())
    }
  }

  val viewState: MutableLiveData<ScreenState> = MutableLiveData(ScreenState.initial())
  private var state: ScreenState by viewState.delegate()

  fun fetchAllNotes() {
    notesDao.loadAll()
      .doOnSubscribe { state = ScreenState.Loading }
      .scheduleIoToUi(schedulersProvider)
      .subscribe(
        { notes -> state = if (notes.isEmpty()) ScreenState.Empty else ScreenState.Content(notes) },
        { error ->
          Timber.e(error)
          offerErrorEvent(error)
        }
      ).disposeOnCleared()
  }


}
