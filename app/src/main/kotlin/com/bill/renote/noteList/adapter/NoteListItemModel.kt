package com.bill.renote.noteList.adapter

import com.bill.renote.R
import com.bill.renote.common.views.epoxy.ViewBindingKotlinModel
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.junctions.NoteWithCategories
import com.bill.renote.databinding.ItemNoteBinding

data class NoteListItemModel(
    private val item: NoteListItemModelData,
    private val onDeleteNoteClicked: (NoteWithCategories) -> Unit,
) : ViewBindingKotlinModel<ItemNoteBinding>(R.layout.item_note) {

    companion object {
        const val SHORT_BODY_LINES = 2
        const val EXPANDED_BODY_LINES = Integer.MAX_VALUE
    }

    override fun ItemNoteBinding.bind() {
        tvNoteTitle.text = item.note.note.title
        tvNoteBody.text = item.note.note.body
        val categoriesTitles = item.note.noteCategories
            .map(CategoryEntity::name)
            .joinToString(separator = System.lineSeparator())
        tvCategoriesNames.text = categoriesTitles

        tvNoteBody.maxLines = if (item.isBodyExpanded) EXPANDED_BODY_LINES else SHORT_BODY_LINES

        btnDeleteNote.setOnClickListener { onDeleteNoteClicked(item.note) }
    }
}



