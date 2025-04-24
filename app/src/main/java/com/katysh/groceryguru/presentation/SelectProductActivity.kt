package com.katysh.groceryguru.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivitySelectProductBinding
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.EntryEditActivity.Companion.PRODUCT_RESULT_KEY
import com.katysh.groceryguru.presentation.recycleview.ProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class SelectProductActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySelectProductBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductsViewModel::class.java]
    }

    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        observeViewModel()

        binding.productsRv.layoutManager = GridLayoutManager(this, 2)
        binding.productsRv.adapter = adapter
        adapter.onClickListener = {
            productClickListener(it)
        }
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            adapter.setProducts(it)
        }
    }

    private fun productClickListener(product: ProductWithPortions) {
        val resultIntent = Intent().apply {
            putExtra(PRODUCT_RESULT_KEY, product)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}