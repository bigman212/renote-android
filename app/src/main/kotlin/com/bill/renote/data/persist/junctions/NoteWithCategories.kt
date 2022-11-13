package com.bill.renote.data.persist.junctions

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.entities.NoteEntity

class NoteWithCategories(
    @Embedded
    val note: NoteEntity,
    @Relation(
        parentColumn = NoteEntity.COLUMN_ID,
        entityColumn = CategoryEntity.COLUMN_ID,
        associateBy = Junction(NoteCategoryCrossRef::class)
    )
    val noteCategories: List<CategoryEntity>
)

