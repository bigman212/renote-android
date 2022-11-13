package com.bill.renote.data.persist.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = CategoryEntity.TABLE_NAME)
data class CategoryEntity(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    val id: String,

    @ColumnInfo(name = COLUMN_NAME)
    val name: String
) {
    companion object {
        const val TABLE_NAME = "categories"

        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"

        fun createNew(name: String = ""): CategoryEntity {
            return CategoryEntity(
                id = UUID.randomUUID().toString(),
                name = name
            )
        }
    }
}
