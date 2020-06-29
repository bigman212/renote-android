package ru.bill.renote.data.di

import ru.bill.renote.data.dao.CategoriesDao
import ru.bill.renote.data.dao.NoteCategoryDao
import ru.bill.renote.data.dao.NotesDao

interface AppDatabaseProvider {
  fun notesDao(): NotesDao
  fun categoriesDao(): CategoriesDao
  fun noteCategoryDao(): NoteCategoryDao
}
