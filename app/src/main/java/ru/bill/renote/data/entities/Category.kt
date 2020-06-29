package ru.bill.renote.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity(tableName = Category.TABLE_NAME)
data class Category(
    @ColumnInfo(name = COLUMN_ID)
    val id: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) {
  companion object {
    const val TABLE_NAME = "categories"

    const val COLUMN_ID = "id"
    const val COLUMN_NAME = "name"

    fun createNew(name: String = ""): Category {
      return Category(
        id = UUID.randomUUID().toString(),
        name = name
      )
    }
  }
}
