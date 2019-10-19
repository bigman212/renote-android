package ru.bill.renote.notes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.bill.renote.App
import ru.bill.renote.model.Resource
import ru.bill.renote.model.dao.NotesDao
import ru.bill.renote.model.entities.Note
import java.util.concurrent.TimeUnit

class NotesViewModel : ViewModel() {
  private val notesDao: NotesDao = App.db.notesDao()

  private val subscriptions = CompositeDisposable()
  private val notes: MutableLiveData<Resource<List<Note>>> = MutableLiveData()

  init {
    subscriptions.add(
      notesDao.all()
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe { notes.value = Resource.loading() }
        .delay(10, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe({
          notes.value = Resource.completed(it)
        }, {
          notes.value = Resource.error(it)
        })
    )
  }

  fun allNotes(): LiveData<Resource<List<Note>>> = notes

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

}