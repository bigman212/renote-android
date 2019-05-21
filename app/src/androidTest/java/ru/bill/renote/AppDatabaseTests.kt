package ru.bill.renote

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Before
import org.junit.Rule
import ru.bill.renote.model.AppDatabase
import ru.bill.renote.model.dao.CategoriesDao
import ru.bill.renote.model.dao.NotesDao
import java.lang.Exception


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */


@RunWith(AndroidJUnit4::class)
class AppDatabaseTests {
    private lateinit var database: AppDatabase
    private lateinit var categoriesDao: CategoriesDao
    private lateinit var notesDao : NotesDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        categoriesDao = database.categoriesDao()
        notesDao = database.notesDao()
    }

    @Test
    fun getNoteWhenTableIsEmpty(){
        notesDao.noteById(10)
            .test()
            .assertNoValues()
    }

    @Test
    fun saveCategoryAndRead() {
        val categoryToSave = EntitiesUtil.createCategory()
        categoriesDao.save(categoryToSave)

        categoriesDao.categoryById(categoryToSave.id)
            .test()
            .assertValue(categoryToSave)
            .dispose()
    }

    @Test
    fun saveCategoriesAndReturnList(){
        val firstCategory = EntitiesUtil.createCategory()
        val secondCategory = EntitiesUtil.createCategory()
        categoriesDao.save(firstCategory)
        categoriesDao.save(secondCategory)

        categoriesDao.all()
            .test()
            .assertValue(arrayListOf(firstCategory, secondCategory))
    }

    @Test
    fun saveNotesAndReturnList() {
        val categoriesToSave = EntitiesUtil.createCategories(5)
        val notesToSave = EntitiesUtil.createNotes(5, categoriesToSave)
        notesToSave.forEach { notesDao.save(it) }

        notesDao.all()
            .test()
            .assertNoErrors()
            .assertValue(notesToSave)
            .dispose()
    }

    @After
    fun closeDatabase(){
        database.close()
    }
}