package com.bill.renote.data.persist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.CategoryEntity.Companion.COLUMN_ID
import com.bill.renote.data.persist.entities.CategoryEntity.Companion.TABLE_NAME

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun loadAll(): List<CategoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categoryEntity: CategoryEntity)

    @Insert
    suspend fun saveAll(categories: List<CategoryEntity>)

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID LIKE :id LIMIT 1")
    suspend fun loadById(id: Long): CategoryEntity?

    @Delete
    suspend fun delete(categoryEntity: CategoryEntity)
}
