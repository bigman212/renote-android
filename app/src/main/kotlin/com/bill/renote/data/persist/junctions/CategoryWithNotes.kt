package com.bill.renote.data.persist.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteEntity

class CategoryWithNotes(
    @Embedded
    val category: CategoryEntity,
    @Relation(
        parentColumn = CategoryEntity.COLUMN_ID,
        entityColumn = NoteEntity.COLUMN_ID,
        associateBy = Junction(NoteCategoryCrossRef::class)
    )
    val notesInCategory: List<NoteEntity>
)