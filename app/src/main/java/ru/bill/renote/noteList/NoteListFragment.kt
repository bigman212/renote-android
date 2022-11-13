package ru.bill.renote.noteList


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getDrawable
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.bill.renote.R
import ru.bill.renote.base.BaseFragment
import ru.bill.renote.base.Event
import ru.bill.renote.base.observeEvents
import ru.bill.renote.common.extensions.observe
import ru.bill.renote.common.extensions.viewModelWithProvider
import ru.bill.renote.common.views.TypedSection
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

  private var isNoteListExtended = false

  private var noteListGroupToView = listOf<NoteListItem>()
  private val section = TypedSection<NoteListItem>()

  override fun onAttach(context: Context) {
    NoteListComponent.init(appComponent).inject(this)
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    setupViews()

    noteListAdapter.add(section)

    observe(viewModel.viewState, ::renderState)
    observeEvents(viewModel.events, ::onEvent)
  }

  private fun setupViews() {
    binding.rvNotes.adapter = noteListAdapter
    binding.rvNotes.itemAnimator = null
    val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
    dividerItemDecoration.setDrawable(getDrawable(requireContext(), R.drawable.divider_note_list)!!)
    binding.rvNotes.addItemDecoration(dividerItemDecoration)

    binding.btnExtend.setOnClickListener {
      section.update(section.getTypedGroups().map(NoteListItem::toItemWithExpandedBody))
      refreshNoteList()
      binding.btnExtend.setTypeface(null, Typeface.BOLD)
      binding.btnCompact.setTypeface(null, Typeface.NORMAL)
    }

    binding.btnCompact.setOnClickListener {
      section.update(section.getTypedGroups().map(NoteListItem::toItemWithShortBody))
      refreshNoteList()
      binding.btnCompact.setTypeface(null, Typeface.BOLD)
      binding.btnExtend.setTypeface(null, Typeface.NORMAL)
    }
  }

  override fun onEvent(event: Event) {
    if (event is NoteListViewModel.NoteIsDeletedEvent) {
      viewModel.fetchAllNotes()
    } else {
      super.onEvent(event)
    }
  }

  override fun onResume() {
    super.onResume()
    viewModel.fetchAllNotes()
  }

  private fun renderState(state: NoteListViewModel.ScreenState) {
    Timber.e(state.toString())
    when (state) {
      is NoteListViewModel.ScreenState.Content -> {
        section.update(state.data.map { NoteListItem(it, viewModel::deleteNote) })
        refreshNoteList()
      }
    }
  }

  private fun refreshNoteList() {
  }

}
