package com.bill.renote.noteList.di

import com.bill.renote.noteList.NoteListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

object NoteListModule {
    fun create() = module {
        viewModelOf(::NoteListViewModel)
    }
}
