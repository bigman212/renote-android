package com.bill.renote.noteList.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.bill.renote.common.views.epoxy.addWithId
import com.bill.renote.data.persist.junctions.NoteWithCategories

class NoteListController(
    private val onDeleteNoteClicked: (NoteWithCategories) -> Unit
) : TypedEpoxyController<List<NoteListItemModelData>>() {

    private var items: List<NoteListItemModelData> = emptyList()

    override fun buildModels(data: List<NoteListItemModelData>) {
        items = if (items.isNotEmpty()) {
            // save old state of expansion
            val isBodyExpanded = items.first().isBodyExpanded
            data.map { it.copy(isBodyExpanded = isBodyExpanded) }
        } else {
            data
        }

        data.forEach { item ->
            NoteListItemModel(
                item = item,
                onDeleteNoteClicked = onDeleteNoteClicked
            )
                .addWithId(item.note.note.id, this)
        }
    }

    fun expandNotes() {
        items = items.map { it.copy(isBodyExpanded = true) }
        setData(items)
    }

    fun compactNotes() {
        items = items.map { it.copy(isBodyExpanded = false) }
        setData(items)
    }
}