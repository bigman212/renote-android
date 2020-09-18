package ru.bill.renote.persist.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import ru.bill.renote.persist.entities.Category
import ru.bill.renote.persist.entities.Note
import ru.bill.renote.persist.entities.NoteCategoryCrossRef

data class NoteWithCategories(
    @Embedded
    val note: Note,

    @Relation(
      parentColumn = Note.COLUMN_ID,
      entityColumn = Category.COLUMN_ID,
      associateBy = Junction(
        value = NoteCategoryCrossRef::class,
        parentColumn = NoteCategoryCrossRef.COLUMN_REF_NOTE_ID,
        entityColumn = NoteCategoryCrossRef.COLUMN_REF_CATEGORY_ID
      )
    )
    val categories: List<Category>
)
