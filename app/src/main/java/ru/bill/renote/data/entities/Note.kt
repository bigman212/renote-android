package ru.bill.renote.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
) {
  @Ignore
  constructor(title: String= "", body: String = "", link: String? = null) : this(0, title, body, link)

  @Ignore
  @Transient
  val compactBody : String = body.take(120)
}
