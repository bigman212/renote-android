package ru.bill.renote.data.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import ru.bill.renote.data.entities.Category
import ru.bill.renote.data.entities.Category.Companion.COLUMN_ID
import ru.bill.renote.data.entities.Category.Companion.TABLE_NAME

@Dao
interface CategoriesDao {
    @Query("SELECT * FROM $TABLE_NAME")
    fun loadAll(): Single<List<Category>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(category: Category): Completable

    @Insert
    fun saveAll(categories: List<Category>): Completable

    @Query("SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID LIKE :id LIMIT 1")
    fun loadById(id: Long): Maybe<Category>

    @Delete
    fun delete(category: Category)
}
