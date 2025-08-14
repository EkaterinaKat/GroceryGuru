package com.katysh.groceryguru.presentation.recycleview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.db.IngredientRepo.IngredientTable
import com.katysh.groceryguru.model.Ingredient

class CalculatorProductAdapter(
    val context: Context
) : RecyclerView.Adapter<IngredientViewHolder>() {

    private var reportTable: IngredientTable = IngredientTable(listOf())
    var onClickListener: ((Ingredient) -> Unit)? = null

    fun setData(reportTable: IngredientTable) {
        Log.i("tag795445", "setData ${reportTable}")
        this.reportTable = reportTable
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        Log.i("tag795445", "onCreateViewHolder")
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.report_line_item, parent, false)
        return IngredientViewHolder(view)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        Log.i("tag795445", "onBindViewHolder")
        val line = reportTable.table[position]
        holder.productTv.text = line.content[0]
        holder.proteinTv.text = line.content[1]
        holder.fatsTv.text = line.content[2]
        holder.carbsTv.text = line.content[3]
        holder.itemView.setOnClickListener {
            line.ingredient?.let {
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
        Log.i("tag795445", "getItemCount ${reportTable.table.size}")
        return reportTable.table.size
    }
}

class IngredientViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val productTv: TextView = itemView.findViewById(R.id.productTv)
    val proteinTv: TextView = itemView.findViewById(R.id.proteinTv)
    val fatsTv: TextView = itemView.findViewById(R.id.fatsTv)
    val carbsTv: TextView = itemView.findViewById(R.id.carbsTv)
}