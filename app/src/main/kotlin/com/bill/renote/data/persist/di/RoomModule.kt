package com.bill.renote.data.persist.di

import androidx.room.Room
import com.bill.renote.data.persist.AppDatabase
import com.bill.renote.data.persist.dao.CategoriesDao
import com.bill.renote.data.persist.dao.NoteCategoryDao
import com.bill.renote.data.persist.dao.NotesDao
import com.bill.renote.data.repository.CategoriesRepository
import com.bill.renote.data.repository.NoteRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

@Suppress("RemoveExplicitTypeArguments")
object RoomModule {

    fun create() = module {
        factoryOf(::RoomTransactionRunner)

        single<AppDatabase> {
            Room.databaseBuilder(
                androidContext(),
                AppDatabase::class.java,
                AppDatabase.DB_NAME
            )
                .build()
        }
        single<NotesDao> { get<AppDatabase>().notesDao }
        single<CategoriesDao> { get<AppDatabase>().categoriesDao }
        single<NoteCategoryDao> { get<AppDatabase>().noteCategoryDao }

        singleOf(::NoteRepository)
        singleOf(::CategoriesRepository)
    }
}
