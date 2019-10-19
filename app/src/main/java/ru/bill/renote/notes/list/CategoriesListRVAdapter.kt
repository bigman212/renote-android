package ru.bill.renote.notes.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import ru.bill.renote.R

class CategoriesListRVAdapter(private val strings: List<String>) : RecyclerView.Adapter<CategoriesListRVAdapter.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int {
        return strings.size
    }

    override fun onBindViewHolder(p0: ViewHolder, p1: Int) {
        p0.bind(strings[p1])
    }

    class ViewHolder(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.recycler_view_categories_item, parent, false))
    {
        fun bind(str: String) {
            itemView.findViewById<Button>(R.id.tv_name).text = str
        }
    }

}