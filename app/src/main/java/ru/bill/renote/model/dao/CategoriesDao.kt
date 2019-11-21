package ru.bill.renote.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bill.renote.model.entities.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun all(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category): Completable

    @Insert
    fun saveAll(categories: List<Category>): Completable

    @Query("SELECT * FROM categories WHERE id=:id LIMIT 1")
    fun categoryById(id: Long): Maybe<Category>

    @Query("DELETE FROM categories WHERE id=:id")
    fun delete(id: Long)
}