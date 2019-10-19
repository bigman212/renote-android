package ru.bill.renote.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
  tableName = "notes"
)
data class Note(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Long,

  @ColumnInfo(name = "title")
  val title: String,

  @ColumnInfo(name = "body")
  val body: String,

  @ColumnInfo(name = "source_link")
  val sourceLink: String?
)