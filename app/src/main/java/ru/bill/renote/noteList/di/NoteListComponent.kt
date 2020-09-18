package ru.bill.renote.noteList.di

import dagger.Component
import ru.bill.renote.di.AppProvider
import ru.bill.renote.noteList.NoteListFragment

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
