package ru.bill.renote.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe
import ru.bill.renote.data.entities.Category

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun all(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category): Completable

    @Insert
    fun saveAll(categories: List<Category>): Maybe<List<Long>>

    @Query("SELECT * FROM categories WHERE id=:id LIMIT 1")
    fun categoryById(id: Long): Maybe<Category>

    @Query("DELETE FROM categories WHERE id=:id")
    fun delete(id: Long)
}
