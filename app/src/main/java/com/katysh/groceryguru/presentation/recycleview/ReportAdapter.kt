package com.katysh.groceryguru.presentation.recycleview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.logic.ReportTable
import com.katysh.groceryguru.model.EntryWithProduct

class ReportAdapter(
    val context: Context
) : RecyclerView.Adapter<ReportViewHolder>() {

    private var reportTable: ReportTable = ReportTable(listOf())
    var onClickListener: ((EntryWithProduct) -> Unit)? = null

    fun setReportTable(reportTable: ReportTable) {
        this.reportTable = reportTable
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.report_line_item, parent, false)
        return ReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        val line = reportTable.table[position]
        holder.productTv.text = line.content[0]
        holder.proteinTv.text = line.content[1]
        holder.fatsTv.text = line.content[2]
        holder.carbsTv.text = line.content[3]
        holder.itemView.setOnClickListener {
            line.entry?.let {
                onClickListener?.invoke(it)
            }
        }

        if (line.drawable != null) {
            holder.itemView.setBackgroundResource(line.drawable)
        } else {
            val color: Int = context.resources.getColor(line.color ?: R.color.white)
            holder.itemView.setBackgroundColor(color)
        }
    }

    override fun getItemCount(): Int {
        return reportTable.table.size
    }
}

class ReportViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val productTv: TextView = itemView.findViewById(R.id.productTv)
    val proteinTv: TextView = itemView.findViewById(R.id.proteinTv)
    val fatsTv: TextView = itemView.findViewById(R.id.fatsTv)
    val carbsTv: TextView = itemView.findViewById(R.id.carbsTv)
}