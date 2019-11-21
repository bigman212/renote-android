package ru.bill.renote.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_notes_item.view.*
import ru.bill.renote.R
import ru.bill.renote.model.entities.Note

class NotesListRVAdapter(private var notesList: MutableList<Note> = mutableListOf())
  : RecyclerView.Adapter<NotesListRVAdapter.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val inflatedView = inflater.inflate(R.layout.rv_notes_item, parent, false)
    return ViewHolder(inflatedView)
  }

  override fun getItemCount(): Int {
    return notesList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(notesList[position])
  }

  fun addAll(notesList: List<Note>) {
    this.notesList.clear()
    this.notesList.addAll(notesList)
    notifyDataSetChanged()
  }


  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(note: Note) {
      itemView.tv_note_title.text = note.title
    }
  }

}

