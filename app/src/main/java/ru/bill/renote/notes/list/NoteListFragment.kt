package ru.bill.renote.notes.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_note_list.*
import ru.bill.renote.R
import ru.bill.renote.model.Resource

class NoteListFragment : Fragment() {
  private lateinit var viewModel: NotesViewModel
  private lateinit var rvCategoriesAdapter: CategoriesListRVAdapter
  private lateinit var rvNotesAdapter: NotesListRVAdapter

  companion object {
    @JvmStatic
    fun newInstance() =
      NoteListFragment().apply {}
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_note_list, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

    btn_fab.setOnClickListener {
      val actionNoteListFragmentToNoteCreateFragment =
        NoteListFragmentDirections.actionNoteListFragmentToNoteCreateFragment(2)
      it.findNavController().navigate(actionNoteListFragmentToNoteCreateFragment)
    }

    rvCategoriesAdapter = CategoriesListRVAdapter(mutableListOf())
    rv_categories.adapter = rvCategoriesAdapter

    rvNotesAdapter = NotesListRVAdapter(mutableListOf())
    rv_notes.adapter = rvNotesAdapter

    viewModel.allCategories().observe(this, Observer { resource ->
      when (resource) {
        is Resource.Success -> {
          rvCategoriesAdapter.addAll(resource.data!!)
        }
      }
    })

    viewModel.allNotes().observe(this, Observer { res ->
      when (res) {
        is Resource.Success -> {
          rvNotesAdapter.addAll(res.data!!)
        }
      }
    })

  }

  override fun onResume() {
    super.onResume()
  }
}
