package ru.bill.renote

import ru.bill.renote.model.entities.Category
import ru.bill.renote.model.entities.Note
import ru.bill.renote.model.entities.NoteCategoryJoin

object EntitiesUtil {
  private var categoryId: Int = 0

  fun createNotes(number: Int): List<Note> {
    val notes = arrayListOf<Note>()
    (1 until number).forEach { i ->
      notes.add(Note(i.toLong(), "title $i", "body $i", "link $i"))
    }
    return notes
  }

   fun createProdNotes(): List<Note> = listOf(
    Note(
      "Test of Smile",
      "1) Just test, smile, smile!\n" +
          "2) IS IT JUST ME OR IS IT GETTING CRAZIER OUT THERE?\n" +
          "3) I USED TO THINK MY LIFE WAS A TRAGEDY...\n" +
          "4) ALL I HAVE ARE NEGATIVE THOUGHTS.\n" +
          "5) YOU WOULDN'T GET IT.\n" +
          "6) YOU GET WHAT YOU F**KING DESERVE!"
    ),
    Note("Testing is hot!", "Keep moving testing!")
  )

  fun createCategory(): Category = Category((++categoryId).toLong(), "body $categoryId")

  fun createCategories(number: Int): List<Category> {
    val categories = arrayListOf<Category>()
    (1..number).forEach { i ->
      categories.add(Category(i.toLong(), "cat name $i"))
    }
    return categories
  }

  fun createProdCategories(): List<Category> {
    return listOf(Category("Hobby"), Category("Music"), Category("Programming"))
  }

  fun createNoteCategoryJoins(notes: List<Note>, category: Category): List<NoteCategoryJoin> {
    return notes.map {
      NoteCategoryJoin(it.id, category.id)
    }
  }

  fun createProdNoteCategoryJoins(notes: List<Note> = createProdNotes(),
                                  categories: List<Category> = createProdCategories()
  ) : List<NoteCategoryJoin>
  {
    // "Science of Smile" -  Hobby, Music
    // "1) Just smile, smile, smile!\n"  -  Hobby
    return listOf(
      NoteCategoryJoin(1, 1),
      NoteCategoryJoin(1, 2),

      NoteCategoryJoin(2, 1)
      )
  }
}