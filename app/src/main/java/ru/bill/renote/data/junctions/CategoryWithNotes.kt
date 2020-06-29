package ru.bill.renote.data.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.bill.renote.data.entities.Category
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.NoteCategoryJoin

data class CategoryWithNotes(
    @Embedded
    val category: Category,

    @Relation(
      parentColumn = Category.COLUMN_ID,
      entity = Note::class,
      entityColumn = Note.COLUMN_ID,
      associateBy = Junction(
        value = NoteCategoryJoin::class,
        parentColumn = NoteCategoryJoin.COLUMN_REF_CATEGORY_ID,
        entityColumn = NoteCategoryJoin.COLUMN_REF_NOTE_ID
      )
    )
    val notes: List<Note>
)
