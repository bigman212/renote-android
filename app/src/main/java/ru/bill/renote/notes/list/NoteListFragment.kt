package ru.bill.renote.notes.list


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_note_list.*
import ru.bill.renote.R

class NoteListFragment : androidx.fragment.app.Fragment() {
  private lateinit var viewModel: NotesViewModel

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
    viewModel.allNotes().observe(this, Observer { resource ->
      resource?.let { result ->
        when {
//                    result.isLoading() -> tv_all.text = "LIOADING"
//                    result.isError() -> tv_all.text = "ERROR"
//                    result.isSuccessful() -> tv_all.text = result.data!!.toString()
        }
      }
    })

    rv_categories.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
      this.context,
      androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL,
      false
    )
    rv_categories.adapter =
      CategoriesListRVAdapter(listOf("CATEGORIES", "SCIENSE", "ALL", "NOTHING", "EVERYTHING"))
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_note_list, container, false)
  }
}
