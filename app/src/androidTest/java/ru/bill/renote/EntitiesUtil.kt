package ru.bill.renote

import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin

object EntitiesUtil {
    private var categoryId: Int = 0

    fun createNotes(
      number: Int
    ): List<Note> {
        val notes = arrayListOf<Note>()
        (1 until number).forEach { i ->
          notes.add(Note(i.toLong(), "title $i", "body $i", "link $i"))
        }
        return notes
    }

    fun createCategory(): Category = Category((++categoryId).toLong(), "body $categoryId")

    fun createCategories(number: Int): List<Category> {
        val categories = arrayListOf<Category>()
        (1..number).forEach { i ->
            categories.add(Category(i.toLong(), "cat name $i"))
        }
        return categories
    }

  fun createNoteCategoryJoins(notes: List<Note>, category: Category): List<NoteCategoryJoin> {
    return notes.map {
      NoteCategoryJoin(it.id, category.id)
    }
  }
}