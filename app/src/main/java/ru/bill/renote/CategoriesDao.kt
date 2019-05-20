package ru.bill.renote

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun all(): LiveData<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category)

    @Query("SELECT * FROM categories WHERE id=:id")
    fun categoryById(id: Long): LiveData<Category>

    @Query("DELETE FROM categories WHERE id=:id")
    fun delete(id: Long)
}