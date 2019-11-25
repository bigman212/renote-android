package ru.bill.renote.notes.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.rv_categories_item.view.*
import ru.bill.renote.R
import ru.bill.renote.model.entities.Category

class CategoriesListRVAdapter(
    private var categoriesList: MutableList<Category> = mutableListOf(),
    private val onCategoryClicked: (clickedCategory: Category) -> Unit = {}
)
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
    holder.bind(categoriesList[position], onCategoryClicked)
  }

  fun addAll(categoriesList: List<Category>) {
    this.categoriesList.clear()
    this.categoriesList.addAll(categoriesList)
    notifyDataSetChanged()
  }

  class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(categoryToBind: Category, onClickListener: (clickedCategory: Category) -> Unit ) {
      itemView.tv_name.text = categoryToBind.name
      itemView.tv_name.textOn = null
      itemView.tv_name.textOff = null
      itemView.tv_name.setOnCheckedChangeListener { compoundButton, b ->
        onClickListener(categoryToBind)
      }
    }
  }
}