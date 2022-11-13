package com.bill.renote.noteList.di

import dagger.Component
import com.bill.renote.di.AppProvider
import com.bill.renote.noteList.NoteListFragment

@Component(dependencies = [AppProvider::class])
interface NoteListComponent {
    fun inject(obj: NoteListFragment)

    @Component.Factory
    interface Factory {
        fun create(appComponent: AppProvider): NoteListComponent
    }

    companion object {
        fun init(appProvider: AppProvider): NoteListComponent {
            return DaggerNoteListComponent
                .factory()
                .create(appProvider)
        }
    }
}
