package ru.bill.renote.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ru.bill.renote.data.AppDatabase
import ru.bill.renote.data.dao.CategoriesDao
import ru.bill.renote.data.dao.NoteCategoryDao
import ru.bill.renote.data.dao.NotesDao
import javax.inject.Singleton

@Module
object RoomModule {
  private const val DB_NAME = "app.db"

  @Singleton
  @Provides
  fun provideRoomDb(context: Context): AppDatabase {
    return Room.databaseBuilder(
      context.applicationContext, AppDatabase::class.java, DB_NAME
    )
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
