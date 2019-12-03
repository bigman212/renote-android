package ru.bill.renote.notes.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import ru.bill.renote.App
import ru.bill.renote.extensions.ioSubscribe
import ru.bill.renote.extensions.uiObserve
import ru.bill.renote.model.Resource
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note

class NotesViewModel : ViewModel() {
  private val notesDao = App.db.notesDao()
  private val categoriesDao = App.db.categoriesDao()
  private val noteCategoryDao = App.db.noteCategoryDao()

  private val clickedCategories: MutableSet<Category> = mutableSetOf()
  private val observableOfCategories = PublishSubject.create<Set<Category>>()

  private val subscriptions = CompositeDisposable()
  private val notes: MutableLiveData<Resource<List<Note>>> = MutableLiveData()
  private val categories: MutableLiveData<Resource<List<Category>>> = MutableLiveData()

  init {
    val subscribeNotes = observableOfCategories
      .doOnSubscribe { categories.value = Resource.Loading() }
      .flatMap { noteCategoryDao.notesForCategories(it.map { cat -> cat.id }) }
      .flatMap {
        if (clickedCategories.isEmpty())
          notesDao.all()
        else
          Observable.just(it)
      }
      .uiObserve()
      .subscribe(
        { notes.value = Resource.Success(it) },
        { notes.value = Resource.Error(it) }
      )
    observableOfCategories.onNext(clickedCategories)

    val subscribeCategories = categoriesDao.all()
      .doOnSubscribe { categories.value = Resource.Loading() }
      .uiObserve()
      .subscribe(
        { categories.value = Resource.Success(it) },
        { categories.value = Resource.Error(it) }
      )

    subscriptions.addAll(subscribeNotes, subscribeCategories)
  }

  fun onCategoryClicked(category: Category) {
    if (category in clickedCategories)
      clickedCategories -= category
    else
      clickedCategories += category
    observableOfCategories.onNext(clickedCategories)
  }

  fun onDeleteClicked(noteToDelete: Note) : Completable {
    return notesDao.delete(noteToDelete.id)
      .ioSubscribe()
      .uiObserve()
  }

  fun allNotes(): LiveData<Resource<List<Note>>> = notes

  fun allCategories(): LiveData<Resource<List<Category>>> = categories

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }

}