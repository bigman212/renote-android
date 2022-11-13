package ru.bill.renote.persist.di

import ru.bill.renote.persist.dao.CategoriesDao
import ru.bill.renote.persist.dao.NoteCategoryDao
import ru.bill.renote.persist.dao.NotesDao

interface DbProvider {
  fun notesDao(): NotesDao
  fun categoriesDao(): CategoriesDao
  fun noteCategoryDao(): NoteCategoryDao
}
