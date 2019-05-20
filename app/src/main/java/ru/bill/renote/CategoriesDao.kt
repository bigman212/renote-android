package ru.bill.renote

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM categories")
    fun all(): Flowable<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category): Completable

    @Query("SELECT * FROM categories WHERE id=:id LIMIT 1")
    fun categoryById(id: Long): Maybe<Category>

    @Query("DELETE FROM categories WHERE id=:id")
    fun delete(id: Long)
}