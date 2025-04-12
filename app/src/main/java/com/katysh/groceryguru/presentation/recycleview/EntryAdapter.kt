package com.katysh.groceryguru.presentation.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.EntryWithProduct

class EntryAdapter : RecyclerView.Adapter<EntryViewHolder>() {

    private var entries: List<EntryWithProduct> = ArrayList()
    var onClickListener: ((EntryWithProduct) -> Unit)? = null

    fun setEntries(entries: List<EntryWithProduct>) {
        this.entries = entries.sortedWith(Comparator.comparing { it.entry.date })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        val titleAndDesc = entry.getInfo()
        holder.textView.text = titleAndDesc
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(entry)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}

class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.textView)

}
