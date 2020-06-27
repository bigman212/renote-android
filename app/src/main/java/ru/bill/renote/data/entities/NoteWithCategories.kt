package ru.bill.renote.data.entities

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
    val categories: List<Category>
)
