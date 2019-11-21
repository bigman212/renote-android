package ru.bill.renote.model.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "categories")
data class Category(
  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "id")
  val id: Long,

  @ColumnInfo(name = "name")
  val name: String
) {
  @Ignore
  constructor(name: String) : this(0, name)
}