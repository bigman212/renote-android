package ru.bill.renote.notes.create

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import ru.bill.renote.App
import ru.bill.renote.extensions.ioSubscribe
import ru.bill.renote.extensions.uiObserve
import ru.bill.renote.model.Resource
import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin


class NoteCreateViewModel : ViewModel() {
  private val notesDao = App.db.notesDao()
  private val categoriesDao = App.db.categoriesDao()
  private val noteCategoryDao = App.db.noteCategoryDao()

  private val clickedCategories: MutableSet<Category> = mutableSetOf()

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

  fun onCategoryClicked(category: Category) {
    clickedCategories += category
  }

  fun saveNewNote(title: String, body: String) {
    val noteToCreate = Note(title, body)
    subscriptions.add(
      notesDao.save(noteToCreate)
        .ioSubscribe()
        .doOnSubscribe { notesSaving.value = Resource.Loading() }
        .uiObserve()
        .flatMapObservable {
          Log.d("TAG", "SAVED NOTE $it")
          saveNoteCategory(it)
        }
        .subscribe {
          Log.d("TAG", "SAVED NOTE CATEGORY $it")
          notesSaving.value = Resource.Success(Completable.complete())
        }
    )
  }

  /**
   * For each category save connected to it note
   */
  private fun saveNoteCategory(id: Long): Observable<Long> =
    Observable.fromIterable(clickedCategories)
      .flatMapMaybe { noteCategoryDao.insert(NoteCategoryJoin(id, it.id)) }
      .ioSubscribe()
      .uiObserve()

  fun noteSavingObservable(): LiveData<Resource<Completable>> = notesSaving

  fun allCategories(): LiveData<Resource<List<Category>>> = categories

  override fun onCleared() {
    super.onCleared()
    subscriptions.clear()
  }
}
