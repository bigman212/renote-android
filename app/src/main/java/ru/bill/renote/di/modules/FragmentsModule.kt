package ru.bill.renote.di.modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.bill.renote.di.AppFragmentFactory
import ru.bill.renote.noteList.NoteListFragment

@Module
abstract class FragmentsModule {
  @Binds
  abstract fun bindFragmentFactory(factory: AppFragmentFactory): FragmentFactory

  @Binds
  @IntoMap
  @FragmentKey(NoteListFragment::class)
  abstract fun bindNoteListFragment(fragment: NoteListFragment): Fragment
}
