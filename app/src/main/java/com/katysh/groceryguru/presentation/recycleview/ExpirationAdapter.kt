package com.katysh.groceryguru.presentation.recycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.ExpirationEntryWithProduct

class ExpirationAdapter : RecyclerView.Adapter<ExpirationViewHolder>() {

    private var entries: List<ExpirationEntryWithProduct> = ArrayList()

    fun setEntries(entries: List<ExpirationEntryWithProduct>) {
        this.entries = entries
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.expiration_item, parent, false)
        return ExpirationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpirationViewHolder, position: Int) {
        val entry = entries[position]
        holder.productTv.text = entry.product.title
        holder.infoTv.text = entry.expirationEntry.getInfo()
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}

class ExpirationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val productTv: TextView
    val infoTv: TextView

    init {
        productTv = itemView.findViewById(R.id.productTv)
        infoTv = itemView.findViewById(R.id.infoTv)
    }
}
