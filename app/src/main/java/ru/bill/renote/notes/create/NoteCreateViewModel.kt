package ru.bill.renote.notes.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import ru.bill.renote.App
import ru.bill.renote.model.Resource
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers


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
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe(
        { categories.value = Resource.Success(it) },
        { categories.value = Resource.Error(it) }
      )

    subscriptions.addAll(subscribeCategories)
  }

  fun saveNewNote(title: String, body: String) {
    val subscribe = notesDao.save(Note(title, body))
      .subscribeOn(Schedulers.io())
      .doOnSubscribe { notesSaving.value = Resource.Loading() }
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe { notesSaving.value = Resource.Success(Completable.complete()) }
    subscriptions.add(subscribe)
  }

  fun noteSavingObservable(): LiveData<Resource<Completable>> = notesSaving

  fun allCategories(): LiveData<Resource<List<Category>>> = categories

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

}
