package ru.bill.renote.model.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class NoteWithCategories(
    @Embedded
    val note: Note,

    @Relation(
      parentColumn = "id",
      entity = Category::class,
      entityColumn = "id",
      associateBy = Junction(
        value = NoteCategoryJoin::class,
        parentColumn = "noteId",
        entityColumn = "categoryId"
      )
    )
    val noteCategories: List<Category>
)