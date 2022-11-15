package com.bill.renote.noteList.adapter

import com.bill.renote.data.persist.junctions.NoteWithCategories

data class NoteListItemModelData(
    val note: NoteWithCategories,
    val isBodyExpanded: Boolean = false,
)