package com.katysh.groceryguru.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityProductsBinding
import com.katysh.groceryguru.presentation.recycleview.ProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ProductsActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityProductsBinding.inflate(layoutInflater)
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

        binding.addButton.setOnClickListener {
            startActivity(ProductEditActivity.newIntent(this))
        }

        observeViewModel()

        binding.productsRv.layoutManager = LinearLayoutManager(this)
        binding.productsRv.adapter = adapter
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            adapter.setProducts(it)
        }
    }
}