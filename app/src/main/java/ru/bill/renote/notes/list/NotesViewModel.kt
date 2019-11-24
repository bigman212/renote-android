package ru.bill.renote.notes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ru.bill.renote.App
import ru.bill.renote.extensions.uiObserve
import ru.bill.renote.model.Resource
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note

class NotesViewModel : ViewModel() {
  private val notesDao = App.db.notesDao()
  private val categoriesDao = App.db.categoriesDao()

  private val subscriptions = CompositeDisposable()
  private val notes: MutableLiveData<Resource<List<Note>>> = MutableLiveData()
  private val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

  init {
    val subscribeNotes = notesDao.all()
      .doOnSubscribe { notes.value = Resource.Loading() }
      .uiObserve()
      .subscribe(
        { notes.value = Resource.Success(it) },
        { notes.value = Resource.Error(it) }
      )

    val subscribeCategories = categoriesDao.all()
      .doOnSubscribe { categories.value = Resource.Loading() }
      .uiObserve()
      .subscribe(
        { categories.value = Resource.Success(it) },
        { categories.value = Resource.Error(it) }
      )

    subscriptions.addAll(subscribeNotes, subscribeCategories)
  }


  fun allNotes(): LiveData<Resource<List<Note>>> = notes

  fun allCategories(): LiveData<Resource<List<Category>>> = categories

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

}