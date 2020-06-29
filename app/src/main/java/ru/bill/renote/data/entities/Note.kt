package ru.bill.renote.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
  tableName = Note.TABLE_NAME
)
data class Note(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_TITLE)
    val title: String,

    @ColumnInfo(name = COLUMN_BODY)
    val body: String,

    @ColumnInfo(name = COLUMN_SOURCE_LINK)
    val sourceLink: String?
) {
  companion object {
    const val TABLE_NAME = "notes"

    const val COLUMN_ID = "id"
    const val COLUMN_TITLE = "title"
    const val COLUMN_BODY = "body"
    const val COLUMN_SOURCE_LINK = "source_link"
  }

  @Ignore
  constructor(title: String = "", body: String = "", link: String? = null)
      : this(0, title, body, link)

  @Ignore
  @Transient
  val compactBody: String = body.take(120)
}
