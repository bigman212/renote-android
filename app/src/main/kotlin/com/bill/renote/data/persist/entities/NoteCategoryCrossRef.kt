package com.bill.renote.data.persist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index

@Entity(
    tableName = NoteCategoryCrossRef.TABLE_NAME,
    primaryKeys = [NoteCategoryCrossRef.COLUMN_REF_NOTE_ID, NoteCategoryCrossRef.COLUMN_REF_CATEGORY_ID],
    indices = [
        Index(NoteCategoryCrossRef.COLUMN_REF_NOTE_ID),
        Index(NoteCategoryCrossRef.COLUMN_REF_CATEGORY_ID)
    ]
)
data class NoteCategoryCrossRef(
    @ColumnInfo(name = NoteEntity.COLUMN_ID)
    val noteId: String,
    @ColumnInfo(name = CategoryEntity.COLUMN_ID)
    val categoryId: String
) {
    companion object {
        const val TABLE_NAME = "note_category_cross_ref"

        const val COLUMN_REF_NOTE_ID = NoteEntity.COLUMN_ID
        const val COLUMN_REF_CATEGORY_ID = CategoryEntity.COLUMN_ID
    }
}