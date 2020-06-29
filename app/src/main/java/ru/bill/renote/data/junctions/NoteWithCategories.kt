package ru.bill.renote.data.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.bill.renote.data.entities.Category
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.NoteCategoryJoin

data class NoteWithCategories(
    @Embedded
    val note: Note,

    @Relation(
      parentColumn = Note.COLUMN_ID,
      entity = Category::class,
      entityColumn = Category.COLUMN_ID,
      associateBy = Junction(
        value = NoteCategoryJoin::class,
        parentColumn = NoteCategoryJoin.COLUMN_REF_NOTE_ID,
        entityColumn = NoteCategoryJoin.COLUMN_REF_CATEGORY_ID
      )
    )
    val categories: List<Category>
)
