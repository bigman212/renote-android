package ru.bill.renote.noteList

import androidx.lifecycle.MutableLiveData
import ru.bill.renote.base.BaseViewModel
import ru.bill.renote.common.extensions.delegate
import ru.bill.renote.common.extensions.scheduleIoToUi
import ru.bill.renote.common.rx.SchedulersProvider
import ru.bill.renote.data.dao.NoteCategoryDao
import ru.bill.renote.data.junctions.NoteWithCategories
import timber.log.Timber
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val notesDao: NoteCategoryDao, val schedulersProvider: SchedulersProvider
) : BaseViewModel() {

  sealed class ScreenState {
    data class Content(val data: List<NoteWithCategories>) : ScreenState()
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
          sendErrorEvent(error)
        }
      ).disposeOnCleared()
  }


}
