package com.bill.renote.noteList

import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bill.renote.R
import com.bill.renote.base.BaseViewModelFragment
import com.bill.renote.base.Event
import com.bill.renote.common.extensions.getDrawable
import com.bill.renote.common.extensions.setTextBold
import com.bill.renote.common.extensions.setTextNormal
import com.bill.renote.common.extensions.unsafeLazy
import com.bill.renote.databinding.FragmentNoteListBinding
import com.bill.renote.noteList.adapter.CategoryListController
import com.bill.renote.noteList.adapter.NoteListController
import org.koin.androidx.viewmodel.ext.android.viewModel

class NoteListFragment : BaseViewModelFragment<NoteListViewModel>() {

    private lateinit var binding: FragmentNoteListBinding
    override val viewModel: NoteListViewModel by viewModel()

    private val noteListController by unsafeLazy { NoteListController(viewModel::startDeleteNote) }
    private val categoryListController = CategoryListController()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_list, container, false).also {
            binding = FragmentNoteListBinding.bind(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        viewModel.viewState.observe(viewLifecycleOwner){
            renderState(it)
        }
//        observe(viewModel.viewState, ::renderState)
    }

    private fun setupViews() {
        with(binding.rvNotes) {
            adapter = noteListController.adapter

            val dividerItemDecoration = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL).apply {
                setDrawable(binding.getDrawable(R.drawable.divider_note_list)!!)
            }
            addItemDecoration(dividerItemDecoration)
        }

        binding.rvCategories.adapter = categoryListController.adapter

        binding.btnExtend.setOnClickListener {
            noteListController.expandNotes()

            binding.btnExtend.setTextBold()
            binding.btnCompact.setTextNormal()
        }

        binding.btnCompact.setOnClickListener {
            noteListController.compactNotes()

            binding.btnCompact.setTextBold()
            binding.btnExtend.setTextNormal()
        }
    }

    private fun renderState(state: NoteListViewModel.ScreenState) {
        binding.progressBar.isVisible = state is NoteListViewModel.ScreenState.Loading
        binding.root.isClickable = state !is NoteListViewModel.ScreenState.Loading
        binding.root.isEnabled = state !is NoteListViewModel.ScreenState.Loading

        when (state) {
            is NoteListViewModel.ScreenState.NotesAndCategories -> {
                noteListController.setData(state.notes)
                categoryListController.setData(state.categories)
                binding.rvCategories.isVisible = true
                binding.containerExtendCompatButtons.isVisible = true
                binding.rvNotes.isVisible = true
            }
            is NoteListViewModel.ScreenState.OnlyCategories -> {
                noteListController.setData(emptyList())
                categoryListController.setData(state.categories)
                binding.rvNotes.isVisible = false
            }
            is NoteListViewModel.ScreenState.OnlyNotes -> {
                noteListController.setData(state.notes)
                categoryListController.setData(emptyList())
                binding.rvCategories.isVisible = false
                binding.containerExtendCompatButtons.isVisible = true
            }
            NoteListViewModel.ScreenState.Empty -> {
                noteListController.setData(emptyList())
                categoryListController.setData(emptyList())
                binding.containerExtendCompatButtons.isVisible = false
                binding.rvCategories.isVisible = false
                binding.rvNotes.isVisible = false
            }
            else -> Unit
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
}
