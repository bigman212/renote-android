package ru.bill.renote.notes.list

import dagger.Component
import ru.bill.renote.di.AppProvider
import javax.inject.Singleton

@Singleton
@Component(dependencies = [AppProvider::class])
interface NoteListComponent {
  fun inject(obj: NoteListFragment)

  @Component.Factory
  interface Factory {
    fun create(
        appComponent:AppProvider
    ): NoteListComponent
  }

  companion object {
    fun init(appProvider: AppProvider): NoteListComponent {
      return DaggerNoteListComponent.factory()
        .create(appProvider)
    }
  }
}
