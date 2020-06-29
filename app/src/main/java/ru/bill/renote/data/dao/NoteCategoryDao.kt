package ru.bill.renote.data.dao

import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Single
import ru.bill.renote.data.entities.Note
import ru.bill.renote.data.entities.NoteCategoryJoin
import ru.bill.renote.data.junctions.NoteWithCategories

@Dao
interface NoteCategoryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(noteCategoryJoin: NoteCategoryJoin): Completable

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(noteCategoryJoins: List<NoteCategoryJoin>): Completable

  @Transaction
  @Query("SELECT * from ${Note.TABLE_NAME}")
  fun loadAll(): Single<List<NoteWithCategories>>
}
