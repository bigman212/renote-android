package ru.bill.renote.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import kotlinx.android.synthetic.main.rv_notes_item.view.*
import ru.bill.renote.R
import ru.bill.renote.extensions.addIfNotExists
import ru.bill.renote.model.entities.Note

class NotesListRVAdapter(private var notesList: MutableList<Note> = mutableListOf(),
                         private val onDeleteClicked: (noteToDelete: Note) -> Unit)
  : RecyclerView.Adapter<NotesListRVAdapter.ViewHolder>() {

  private val viewBinderHelper = ViewBinderHelper()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val inflatedView = inflater.inflate(R.layout.rv_notes_item, parent, false)
    return ViewHolder(inflatedView)
  }

  override fun getItemCount(): Int {
    return notesList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val cellIsWhite = position % 2 != 0 // begins from 0
    val noteToBind = notesList[position]

    with(viewBinderHelper) {
      setOpenOnlyOne(true)
      bind(holder.itemView.swipe_layout, noteToBind.id.toString())
      closeLayout(noteToBind.id.toString())
    }
    holder.itemView.btn_delete_note.setOnClickListener {onDeleteClicked(noteToBind)}
    holder.bind(noteToBind, cellIsWhite)
  }

  fun addAll(notesList: List<Note>) {
    this.notesList.clear()
    this.notesList.addAll(notesList)
    notifyDataSetChanged()
  }

  fun add(note: Note){
    this.notesList.addIfNotExists(note)
    notifyItemInserted(notesList.indexOf(note))
  }

  fun remove(note: Note){
    val indexOfElement = notesList.indexOf(note)
    if (indexOfElement != -1){
      this.notesList.remove(note)
      notifyItemRemoved(indexOfElement)
    }
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(note: Note, cellIsNotEven: Boolean) {
      with(itemView) {
        tv_note_title.text = note.title
        tv_note_body_cut.text = note.body.take(120)
        tv_note_body_cut.gradientEnabled = note.body.length > 120

        val color =
          if (cellIsNotEven)
            R.color.colorEven
          else
            R.color.white

        setBackgroundColor(ResourcesCompat.getColor(itemView.resources, color, null))
      }


    }
  }
}

