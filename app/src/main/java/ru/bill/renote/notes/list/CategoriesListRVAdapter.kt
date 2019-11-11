package ru.bill.renote.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_categories_item.view.*
import ru.bill.renote.R

class CategoriesListRVAdapter(private val categoriesList: List<String>)
  : RecyclerView.Adapter<CategoriesListRVAdapter.ViewHolder>()
{
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val inflater = LayoutInflater.from(parent.context)
    val inflatedView = inflater.inflate(R.layout.rv_categories_item, parent, false)
    return ViewHolder(inflatedView)
  }

  override fun getItemCount(): Int {
    return categoriesList.size
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(categoriesList[position])
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(categoryTitle: String) {
      itemView.tv_name.text = categoryTitle
    }
  }
}