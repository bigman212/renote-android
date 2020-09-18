package ru.bill.renote.noteList.adapter

import android.view.View
import com.xwray.groupie.viewbinding.BindableItem
import ru.bill.renote.R
import ru.bill.renote.data.junctions.NoteWithCategories
import ru.bill.renote.databinding.RvNotesItemBinding

class NoteListItem(private val noteToBind: NoteWithCategories) : BindableItem<RvNotesItemBinding>() {

  override fun getLayout(): Int = R.layout.rv_notes_item

  override fun initializeViewBinding(view: View): RvNotesItemBinding = RvNotesItemBinding.bind(view)

  override fun bind(viewBinding: RvNotesItemBinding, position: Int) {
    with(viewBinding) {
      tvNoteTitle.text = noteToBind.note.title
      tvNoteBody.text = noteToBind.note.body
      tvNoteCategoryName.text = noteToBind.categories.joinToString(separator = System.lineSeparator())
    }
  }
}
