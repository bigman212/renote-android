package ru.bill.renote.persist.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.bill.renote.persist.AppDatabase
import ru.bill.renote.persist.PrepopulateCallback
import ru.bill.renote.persist.dao.CategoriesDao
import ru.bill.renote.persist.dao.NoteCategoryDao
import ru.bill.renote.persist.dao.NotesDao
import javax.inject.Singleton

@Module
object RoomModule {

  @Singleton
  @Provides
  fun provideRoomDb(applicationContext: Context): AppDatabase {
    return Room
      .databaseBuilder(applicationContext, AppDatabase::class.java, AppDatabase.DB_NAME)
      .addCallback(PrepopulateCallback())
      .build()
  }

  @Singleton
  @Provides
  fun provideNotesDao(database: AppDatabase): NotesDao = database.notesDao()

  @Singleton
  @Provides
  fun provideCategoriesDao(database: AppDatabase): CategoriesDao = database.categoriesDao()

  @Singleton
  @Provides
  fun provideNoteCategoryDao(database: AppDatabase): NoteCategoryDao = database.noteCategoryDao()
}
