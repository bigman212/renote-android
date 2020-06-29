package ru.bill.renote.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = Category.TABLE_NAME)
data class Category(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    val id: Long,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) {
  companion object {
    const val TABLE_NAME = "categories"

    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"
  }

  @Ignore
  constructor(name: String) : this(0, name)
}
