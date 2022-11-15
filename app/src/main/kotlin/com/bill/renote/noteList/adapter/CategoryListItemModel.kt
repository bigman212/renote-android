package com.bill.renote.noteList.adapter

import com.bill.renote.R
import com.bill.renote.common.views.epoxy.ViewBindingKotlinModel
import com.bill.renote.data.persist.junctions.CategoryWithNotes
import com.bill.renote.databinding.ItemCategoryBinding

data class CategoryListItemModel(
    private val item: CategoryWithNotes,
) : ViewBindingKotlinModel<ItemCategoryBinding>(R.layout.item_category) {

    override fun ItemCategoryBinding.bind() {
        tvName.text = item.category.name + "(${item.notesInCategory.size})"
    }
}