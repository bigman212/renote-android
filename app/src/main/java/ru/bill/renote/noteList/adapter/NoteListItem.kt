package ru.bill.renote.noteList.adapter

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.bill.renote.R
import ru.bill.renote.databinding.RvNotesItemBinding
import ru.bill.renote.persist.entities.Category
import ru.bill.renote.persist.junctions.NoteWithCategories
import java.util.*


data class NoteListItem(
    private val itemToBind: NoteWithCategories,
    private val onNoteDeleteClick: (NoteWithCategories) -> Unit,
    private val bodyIsExpanded: Boolean = false,
) : BindableItem<RvNotesItemBinding>() {

  override fun getId(): Long {
    return UUID.fromString(itemToBind.note.id).mostSignificantBits and Long.MAX_VALUE
  }

  companion object {
    const val SHORT_BODY_LINES = 2
    const val EXPANDED_BODY_LINES = Integer.MAX_VALUE
  }

  fun toItemWithExpandedBody(): NoteListItem = copy(bodyIsExpanded = true)

  fun toItemWithShortBody(): NoteListItem = copy(bodyIsExpanded = false)

  override fun bind(viewBinding: RvNotesItemBinding, position: Int) {
    with(viewBinding) {
      tvNoteTitle.text = itemToBind.note.title

      tvNoteBody.maxLines = if (bodyIsExpanded) EXPANDED_BODY_LINES else SHORT_BODY_LINES
      tvNoteBody.text = itemToBind.note.body

      val categoriesTitles = itemToBind.categories
        .map(Category::name)
        .joinToString(separator = System.lineSeparator())
      tvNoteCategoriesNames.text = categoriesTitles

      btnDeleteNote.setOnClickListener { onNoteDeleteClick.invoke(itemToBind) }
    }
  }

  override fun getLayout(): Int = R.layout.rv_notes_item

  override fun initializeViewBinding(view: View): RvNotesItemBinding = RvNotesItemBinding.bind(view)
}
