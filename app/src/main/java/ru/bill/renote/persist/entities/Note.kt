package ru.bill.renote.persist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.*

@Entity(
  tableName = Note.TABLE_NAME
)
data class Note(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,

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

    fun createNew(title: String = "", body: String = "", sourceLink: String? = null): Note {
      val uuid = UUID.randomUUID().toString()
      return Note(uuid, title, body, sourceLink)
    }
  }

  @Ignore
  @Transient
  val compactBody: String = body.take(120)
}
