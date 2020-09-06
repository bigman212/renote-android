package ru.bill.renote.noteList


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import ru.bill.renote.R
import ru.bill.renote.base.BaseFragment
import ru.bill.renote.base.observeEvents
import ru.bill.renote.databinding.FragmentNoteListBinding
import ru.bill.renote.noteList.di.NoteListComponent
import ru.bill.renote.util.observe
import ru.bill.renote.util.viewBinding
import timber.log.Timber
import javax.inject.Inject

class NoteListFragment : BaseFragment(R.layout.fragment_note_list) {

  val binding: FragmentNoteListBinding by viewBinding<FragmentNoteListBinding>()
  private val noteListAdapter = GroupAdapter<GroupieViewHolder>()

  @Inject
  internal lateinit var viewModelFactory: ViewModelProvider.Factory

  private val viewModel: NoteListViewModel by viewModels { viewModelFactory }


  override fun onAttach(context: Context) {
    super.onAttach(context)
    NoteListComponent.init(appComponent).inject(this)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    binding.rvNotes.adapter = noteListAdapter

    observe(viewModel.viewState, ::renderState)
    observeEvents(viewModel.events, ::onEvent)
  }

  private fun renderState(state: NoteListViewModel.ScreenState) {
    Timber.e(state.toString())
  }

}
