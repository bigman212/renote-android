package ru.bill.renote.model.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
  tableName = "note_category_join",
  primaryKeys = ["noteId", "categoryId"],
  foreignKeys = [
    ForeignKey(
      entity = Note::class,
      parentColumns = ["id"],
      childColumns = ["noteId"]
    ),
    ForeignKey(
      entity = Note::class,
      parentColumns = ["id"],
      childColumns = ["categoryId"]
    )
  ],
  indices = [Index("noteId"), Index("categoryId")]
)
data class NoteCategoryJoin(
  val noteId: Long,
  val categoryId: Long
)