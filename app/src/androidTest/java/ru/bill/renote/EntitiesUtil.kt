package ru.bill.renote

import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note

object EntitiesUtil {
    private var categoryId: Int = 0

    fun createNotes(number: Int, categories: List<Category> = createCategories(number)): List<Note> {
        val notes = arrayListOf<Note>()
        (1 until number).forEach { i ->
            notes.add(Note(i.toLong(), "title $i", "body $i", categories[i], "link $i"))
        }
        return notes
    }

    fun createCategory(): Category = Category((++categoryId).toLong(), "body $categoryId")

    fun createCategories(number: Int): List<Category>{
        val categories = arrayListOf<Category>()
        (1..number).forEach { i ->
            categories.add(Category(i.toLong(), "cat name $i"))
        }
        return categories
    }
}