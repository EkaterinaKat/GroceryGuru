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
        this.products = products
        notifyDataSetChanged()

        Log.i("tag7931", "setProducts $products")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.textView.text = product.title
        holder.itemView.setOnClickListener {
            onClickListener?.invoke(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}

class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val textView: TextView = itemView.findViewById(R.id.productTitleTv)
}
