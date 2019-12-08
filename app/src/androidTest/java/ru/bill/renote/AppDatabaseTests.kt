package ru.bill.renote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import ru.bill.renote.model.AppDatabase
import ru.bill.renote.model.dao.CategoriesDao
import ru.bill.renote.model.dao.NoteCategoryDao
import ru.bill.renote.model.dao.NotesDao


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class AppDatabaseTests {
  private lateinit var database: AppDatabase
  private lateinit var categoriesDao: CategoriesDao
  private lateinit var notesDao: NotesDao
  private lateinit var noteCategoryDao: NoteCategoryDao

  @get:Rule
  var instantTaskExecutorRule = InstantTaskExecutorRule()

  @Before
  fun initDatabase() {
    database = Room.inMemoryDatabaseBuilder(
      InstrumentationRegistry.getInstrumentation().context,
      AppDatabase::class.java
    )
      .allowMainThreadQueries()
      .build()
    categoriesDao = database.categoriesDao()
    notesDao = database.notesDao()
    noteCategoryDao = database.noteCategoryDao()
  }

  @Test
  fun getNoteWhenTableIsEmpty() {
    notesDao.noteById(10)
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertNoValues()
  }

  @Test
  fun saveCategoryAndRead() {
    val categoryToSave = EntitiesUtil.createCategory()
    categoriesDao.save(categoryToSave)
      .test()
      .assertComplete()
      .dispose()

    categoriesDao.categoryById(categoryToSave.id)
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertValue(categoryToSave)
      .dispose()
  }

  @Test
  fun saveCategoriesAndReturnList() {
    val firstCategory = EntitiesUtil.createCategory()
    val secondCategory = EntitiesUtil.createCategory()
    categoriesDao.saveAll(listOf(firstCategory, secondCategory))
      .test()
      .assertComplete()

    categoriesDao.all()
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertValue(arrayListOf(firstCategory, secondCategory))
      .dispose()
  }

  @Test
  fun saveNotesAndCategoriesAndReturnLists() {
    val categoriesToSave = EntitiesUtil.createCategories(5)
    val notesToSave = EntitiesUtil.createNotes(5)

    notesDao.saveAll(notesToSave)
      .test()
      .assertNoErrors()
      .assertComplete()

    categoriesDao.saveAll(categoriesToSave)
      .test()
      .assertNoErrors()
      .assertComplete()

    notesDao.all()
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertValue(notesToSave)
      .dispose()

    categoriesDao.all()
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertValue(categoriesToSave)
      .dispose()
  }

  @Test
  fun saveNoteCategoryJoinsAndReturnList() {
    val categoriesToSave = EntitiesUtil.createProdCategories()
    val notesToSave = EntitiesUtil.createProdNotes()
    notesDao.saveAll(notesToSave)
      .test()
      .assertNoErrors()
      .assertComplete()

    categoriesDao.saveAll(categoriesToSave)
      .test()
      .assertNoErrors()
      .assertComplete()

    val noteCategoryJoins = EntitiesUtil.createProdNoteCategoryJoins()
    noteCategoryJoins.forEach { noteCategoryDao.insert(it).test().assertNoErrors() }


    noteCategoryDao
      .notesWithCategory()
      .doOnNext{ println(it) }
      .test()
      .assertComplete()

    noteCategoryDao.notesForCategory(categoriesToSave.first().id)
      .test()
      .assertSubscribed()
      .assertNoErrors()
      .assertValue(notesToSave)
      .dispose()
  }

  @After
  fun closeDatabase() {
    database.close()
  }
}
