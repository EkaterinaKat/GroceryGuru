package com.katysh.groceryguru.presentation.recycleview

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct
import com.katysh.groceryguru.util.daysLeft
import com.katysh.groceryguru.util.getDateString
import com.katysh.groceryguru.util.isNotBlank
import java.util.Date

class ExpirationAdapter : RecyclerView.Adapter<ExpirationViewHolder>() {

    private val expDateDaysLeftCache = mutableMapOf<Date, Int>()

    private var entries: List<ExpirationEntryWithProduct> = ArrayList()
    var onClickListener: ((ExpirationEntryWithProduct) -> Unit)? = null

    fun setEntries(entries: List<ExpirationEntryWithProduct>) {
        this.entries = entries.sortedWith(Comparator.comparing { getDaysLeft(it.expirationEntry) })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpirationViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.expiration_item, parent, false)
        return ExpirationViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExpirationViewHolder, position: Int) {
        val entry = entries[position]
        val titleAndDesc = getTitleAndDesc(entry)
        holder.productTv.text = titleAndDesc.first
        holder.infoTv.text = titleAndDesc.second
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(entry)
        }
        holder.itemView.background = getBackground(
            getDaysLeft(entry.expirationEntry),
            holder.itemView.context
        )
    }

    fun getBackground(daysLeft: Int, context: Context): Drawable? {
        val drawableId = when (daysLeft) {
            0, 1 -> R.drawable.order_0
            2 -> R.drawable.order_1
            3 -> R.drawable.order_2
            else -> R.drawable.order_3
        }
        return ContextCompat.getDrawable(context, drawableId)
    }

    private fun getTitleAndDesc(entry: ExpirationEntryWithProduct): Pair<String, String> {

        val title = "${entry.product.title} (${getDaysLeft(entry.expirationEntry)})"

        val descBuilder = StringBuilder()
        if (isNotBlank(entry.expirationEntry.comment)) {
            descBuilder.append(entry.expirationEntry.comment).append("\n\n")
        }
        descBuilder
            .append(getDateString(entry.expirationEntry.startDate))
            .append(" - ")
            .append(getDateString(entry.expirationEntry.expirationDate))

        return Pair(title, descBuilder.toString())
    }

    private fun getDaysLeft(entry: ExpirationEntry): Int {
        val expDate = entry.expirationDate ?: return 0
        return expDateDaysLeftCache.getOrPut(expDate) {
            daysLeft(expDate)
        }
    }

    override fun getItemCount(): Int {
        return entries.size
    }
}

class ExpirationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val productTv: TextView = itemView.findViewById(R.id.productTv)
    val infoTv: TextView = itemView.findViewById(R.id.infoTv)

}
