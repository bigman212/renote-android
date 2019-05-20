package ru.bill.renote.model.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Maybe
import ru.bill.renote.model.entities.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun all(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category)

    @Query("SELECT * FROM categories WHERE id=:id LIMIT 1")
    fun categoryById(id: Long): Maybe<Category>

    @Query("DELETE FROM categories WHERE id=:id")
    fun delete(id: Long)
}