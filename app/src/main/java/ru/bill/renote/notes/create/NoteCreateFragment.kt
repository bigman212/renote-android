package ru.bill.renote.notes.create

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import kotlinx.android.synthetic.main.fragment_note_create.*
import ru.bill.renote.R
import ru.bill.renote.model.Resource
import ru.bill.renote.notes.list.CategoriesListRVAdapter


class NoteCreateFragment : Fragment() {
  private lateinit var viewModel: NoteCreateViewModel
  private lateinit var rvCategoriesAdapter: CategoriesListRVAdapter
  private lateinit var navController: NavController

  companion object {
    @JvmStatic
    fun newInstance() = NoteCreateFragment().apply {}
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val contextThemeWrapper = ContextThemeWrapper(activity, R.style.Theme_Create_Note_Screen)
    val localInflater = inflater.cloneInContext(contextThemeWrapper)
    return localInflater.inflate(R.layout.fragment_note_create, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(NoteCreateViewModel::class.java)

    rvCategoriesAdapter = CategoriesListRVAdapter(onCategoryClicked = viewModel::onCategoryClicked)
    rv_categories.adapter = rvCategoriesAdapter

    viewModel.allCategories().observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Success -> {
          rvCategoriesAdapter.addAll(it.data!!)
        }
      }
    })

    viewModel.noteSavingObservable().observe(viewLifecycleOwner, Observer {
      when (it) {
        is Resource.Success -> {
          Toast.makeText(context, "Note is saved", Toast.LENGTH_LONG).show()
          navController.navigateUp()
        }
      }
    })

    btn_add_note.setOnClickListener {
      val editText = view.findViewById<EditText>(R.id.et_note_title)
      val editText2 = view.findViewById<EditText>(R.id.et_note_body)
      viewModel.saveNewNote(editText.text.toString(), editText2.text.toString())
    }
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    Log.e("tag", "clicked")
    when (item.itemId) {
      android.R.id.home -> {
        navController.navigateUp()
        return true
      }
      else -> return super.onOptionsItemSelected(item)
    }
  }
}