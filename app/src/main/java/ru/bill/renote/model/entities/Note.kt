package ru.bill.renote.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "notes"
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val noteId: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "body")
    val body: String,

    @Embedded(prefix = "category_")
    val category: Category,

    @ColumnInfo(name = "source_link")
    val sourceLink: String?
)