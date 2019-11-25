package ru.bill.renote.notes.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_note_create.*
import ru.bill.renote.R
import ru.bill.renote.model.Resource
import ru.bill.renote.notes.list.CategoriesListRVAdapter

class NoteCreateFragment : Fragment() {
    private lateinit var viewModel: NoteCreateViewModel
    private lateinit var rvCategoriesAdapter: CategoriesListRVAdapter

    companion object {
        @JvmStatic
        fun newInstance() = NoteCreateFragment().apply {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_create, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NoteCreateViewModel::class.java)

        rvCategoriesAdapter = CategoriesListRVAdapter(onCategoryClicked = viewModel::onCategoryClicked)
        rv_categories.adapter = rvCategoriesAdapter

        viewModel.allCategories().observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Success -> {
                    rvCategoriesAdapter.addAll(it.data!!)
                }
            }
        })

        viewModel.noteSavingObservable().observe(viewLifecycleOwner, Observer {
            when (it){
                is Resource.Success -> {
                    Toast.makeText(context, "готово", Toast.LENGTH_LONG).show()
                }
            }
        })

        btn_add_note.setOnClickListener {
            val editText = view.findViewById<EditText>(R.id.et_note_title)
            val editText2 = view.findViewById<EditText>(R.id.et_note_body)
            viewModel.saveNewNote(editText.text.toString(), editText2.text.toString())
        }
    }
}