package ru.bill.renote.noteList


import android.content.Context
import android.os.Bundle
import android.view.View
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.bill.renote.R
import ru.bill.renote.base.BaseFragment
import ru.bill.renote.base.observeEvents
import ru.bill.renote.common.extensions.observe
import ru.bill.renote.common.extensions.viewModelWithProvider
import ru.bill.renote.common.views.viewBinding
import ru.bill.renote.databinding.FragmentNoteListBinding
import ru.bill.renote.noteList.adapter.NoteListItem
import ru.bill.renote.noteList.di.NoteListComponent
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class NoteListFragment @Inject constructor(private val viewModelProvider: Provider<NoteListViewModel>) : BaseFragment(R.layout.fragment_note_list) {

  private val binding: FragmentNoteListBinding by viewBinding<FragmentNoteListBinding>()
  private val noteListAdapter = GroupAdapter<GroupieViewHolder>()

  private val viewModel: NoteListViewModel by viewModelWithProvider { viewModelProvider.get() }

  override fun onAttach(context: Context) {
    NoteListComponent.init(appComponent).inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvNotes.adapter = noteListAdapter

    observe(viewModel.viewState, ::renderState)
    observeEvents(viewModel.events, ::onEvent)

    viewModel.fetchAllNotes()
  }

  private fun renderState(state: NoteListViewModel.ScreenState) {
    Timber.e(state.toString())
    when (state) {
      is NoteListViewModel.ScreenState.Content -> {
        noteListAdapter.update(state.data.map(::NoteListItem))
      }
    }
  }

}
