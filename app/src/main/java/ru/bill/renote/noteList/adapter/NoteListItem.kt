package ru.bill.renote.noteList.adapter

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.bill.renote.R
import ru.bill.renote.persist.junctions.NoteWithCategories
import ru.bill.renote.databinding.RvNotesItemBinding
import ru.bill.renote.persist.entities.Category

class NoteListItem(private val noteToBind: NoteWithCategories) : BindableItem<RvNotesItemBinding>() {

  override fun getLayout(): Int = R.layout.rv_notes_item

  override fun initializeViewBinding(view: View): RvNotesItemBinding = RvNotesItemBinding.bind(view)

  override fun bind(viewBinding: RvNotesItemBinding, position: Int) {
    with(viewBinding) {
      tvNoteTitle.text = noteToBind.note.title
      tvNoteBody.text = noteToBind.note.body
      val categoriesTitles = noteToBind.categories
        .map(Category::name)
        .joinToString(separator = System.lineSeparator())
      tvNoteCategoryName.text = categoriesTitles
    }
  }
}
