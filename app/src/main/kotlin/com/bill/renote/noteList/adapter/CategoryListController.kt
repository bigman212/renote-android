package com.bill.renote.noteList.adapter

import com.airbnb.epoxy.TypedEpoxyController
import com.bill.renote.common.views.epoxy.addWithId
import com.bill.renote.data.persist.junctions.CategoryWithNotes

class CategoryListController(
    private val onDeleteNoteClicked: (CategoryWithNotes) -> Unit = {}
) : TypedEpoxyController<List<CategoryWithNotes>>() {

    override fun buildModels(data: List<CategoryWithNotes>) {
        data.forEach { item ->
            CategoryListItemModel(item)
                .addWithId(item.category.id, this)
        }
    }
}