package ru.bill.renote.notes.list


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.WorkerThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_note_list.*
import ru.bill.renote.R
import ru.bill.renote.model.Resource

class NoteListFragment : Fragment() {
  private lateinit var viewModel: NotesViewModel
  private lateinit var rvAdapter: CategoriesListRVAdapter

  companion object {
    @JvmStatic
    fun newInstance() =
      NoteListFragment().apply {
        //                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
      }
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(NotesViewModel::class.java)

    btn_fab.setOnClickListener {
      val actionNoteListFragmentToNoteCreateFragment =
        NoteListFragmentDirections.actionNoteListFragmentToNoteCreateFragment(2)
      it.findNavController().navigate(actionNoteListFragmentToNoteCreateFragment)
    }

    rvAdapter = CategoriesListRVAdapter(mutableListOf())
    rv_categories.adapter = rvAdapter

    viewModel.allCategories().observe(this, Observer { resource ->
      when (resource){
        is Resource.Success -> {
          rvAdapter.addAll(resource.data!!)
        }
      }
    })

  }

  override fun onResume() {
    super.onResume()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_note_list, container, false)
  }
}
