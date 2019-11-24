package ru.bill.renote.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.disposables.CompositeDisposable
import ru.bill.renote.App
import ru.bill.renote.extensions.ioSubscribe
import ru.bill.renote.extensions.uiObserve
import ru.bill.renote.model.Resource
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note


class NoteCreateViewModel : ViewModel(){
  private val notesDao = App.db.notesDao()
  private val categoriesDao = App.db.categoriesDao()
  private val noteCategoryDao = App.db.noteCategoryDao()

  private val subscriptions = CompositeDisposable()
  private val notesSaving: MutableLiveData<Resource<Completable>> = MutableLiveData()
  private val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

  init {
    val subscribeCategories = categoriesDao.all()
      .doOnSubscribe { categories.value = Resource.Loading() }
      .uiObserve()
      .subscribe(
        { categories.value = Resource.Success(it) },
        { categories.value = Resource.Error(it) }
      )

    subscriptions.add(subscribeCategories)
  }

  fun saveNewNote(title: String, body: String) {
    subscriptions.add(notesDao.save(Note(title, body))
      .ioSubscribe()
      .doOnSubscribe { notesSaving.value = Resource.Loading() }
      .uiObserve()
      .subscribe { notesSaving.value = Resource.Success(Completable.complete()) }
    )
  }

  fun noteSavingObservable(): LiveData<Resource<Completable>> = notesSaving

  fun allCategories(): LiveData<Resource<List<Category>>> = categories

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

}
