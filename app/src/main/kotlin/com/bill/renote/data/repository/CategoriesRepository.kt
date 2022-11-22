package com.bill.renote.data.repository

import com.bill.renote.data.persist.dao.CategoriesDao
import com.bill.renote.data.persist.dao.NoteCategoryDao
import com.bill.renote.data.persist.entities.CategoryEntity
import com.bill.renote.data.persist.entities.NoteCategoryCrossRef
import com.bill.renote.data.persist.junctions.CategoryWithNotes
import java.util.UUID

class CategoriesRepository(
    private val categoriesDao: CategoriesDao,
    private val noteCategoryDao: NoteCategoryDao
) {
    suspend fun getAllCategories(): List<CategoryEntity> = categoriesDao.loadAll()

    suspend fun getAllCategoriesWithNotes(): List<CategoryWithNotes> = noteCategoryDao.loadAllCategoriesWithNotes()

    suspend fun createCategory(categoryName: String): CategoryEntity {
        val entity = CategoryEntity(
            id = UUID.randomUUID().toString(),
            name = categoryName
        )
        categoriesDao.save(entity)
        return entity
    }

    suspend fun linkNoteToCategory(noteId: String, categoryId: String) {
        noteCategoryDao.insert(NoteCategoryCrossRef(noteId = noteId, categoryId = categoryId))
    }

    suspend fun isEmpty(): Boolean = categoriesDao.loadAll().isEmpty()
}