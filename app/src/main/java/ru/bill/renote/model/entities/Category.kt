package ru.bill.renote.model.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
@Entity(tableName = "categories")
data class Category (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id: Long,

    @ColumnInfo(name = "name")
    val name: String
)