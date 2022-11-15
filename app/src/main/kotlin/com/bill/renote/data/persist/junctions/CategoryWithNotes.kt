package com.bill.renote.data.persist.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteEntity

class CategoryWithNotes(
    @Embedded
    var category: CategoryEntity,
    @Relation(
        parentColumn = CategoryEntity.COLUMN_ID,
        entityColumn = NoteCategoryCrossRef.COLUMN_REF_NOTE_ID,
        associateBy = Junction(NoteCategoryCrossRef::class)
    )
    val notesInCategory: List<NoteEntity>
)