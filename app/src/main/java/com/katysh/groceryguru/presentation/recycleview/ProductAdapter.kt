package com.katysh.groceryguru.presentation.recycleview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.Product

class ProductAdapter : RecyclerView.Adapter<ProductViewHolder>() {

    private var products: List<Product> = ArrayList()
    var onClickListener: ((Product) -> Unit)? = null

    fun setProducts(products: List<Product>) {
        Log.i("tag984521", "setProducts")
        this.products = products
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        Log.i("tag984521", "onCreateViewHolder")
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        Log.i("tag984521", "onBindViewHolder")
        val product = products[position]
        holder.textView.text = product.getFullInfo()
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        Log.i("tag984521", "getItemCount")
        return products.size
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.productTitleTv)
}
