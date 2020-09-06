package ru.bill.renote.common.vm

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import ru.bill.renote.noteList.NoteListViewModel

@Module
abstract class ViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(NoteListViewModel::class)
    internal abstract fun bind(viewModel: NoteListViewModel): ViewModel
}
