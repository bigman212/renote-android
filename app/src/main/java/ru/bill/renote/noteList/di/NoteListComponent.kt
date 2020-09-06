package ru.bill.renote.noteList.di

import dagger.Component
import ru.bill.renote.common.vm.ViewModelFactoryModule
import ru.bill.renote.common.vm.ViewModelsModule
import ru.bill.renote.di.AppProvider
import ru.bill.renote.noteList.NoteListFragment
import javax.inject.Singleton

@Singleton
@Component(dependencies = [AppProvider::class], modules = [ViewModelsModule::class, ViewModelFactoryModule::class])
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
