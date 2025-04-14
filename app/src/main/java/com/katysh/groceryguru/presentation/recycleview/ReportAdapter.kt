package com.katysh.groceryguru.presentation.recycleview


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.katysh.groceryguru.R


class ReportAdapter(
    private val context: Context,
    private val table: List<List<String>>
) : BaseAdapter() {

    private val items: Array<String>

    init {
        val itemList = mutableListOf<String>()
        for (line in table) {
            itemList.addAll(line)
        }

        items = itemList.toTypedArray()
    }

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): Any = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.grid_item, parent, false)
        }

        val textView: TextView = view!!.findViewById(R.id.textView)
        textView.text = items[position]

        return view
    }
}