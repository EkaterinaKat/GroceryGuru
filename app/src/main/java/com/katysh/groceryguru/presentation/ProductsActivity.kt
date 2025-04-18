package com.katysh.groceryguru.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityProductsBinding
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.presentation.recycleview.ProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.copyToClipboard
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

        binding.productsRv.layoutManager = GridLayoutManager(this, 2)
        binding.productsRv.adapter = adapter
        adapter.onClickListener = {
            productClickListener(it)
        }

//        binding.backupButton.setOnClickListener { viewModel.backup() }
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            adapter.setProducts(it)
        }
        viewModel.errorLD.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.backupLD.observe(this) {
            copyToClipboard(this, it)
            Toast.makeText(this, "Backup copied to clipboard", Toast.LENGTH_LONG).show()
        }
    }

    private fun productClickListener(product: Product) {
        val dialog = ProductMenuDialog(this, product, viewModel) {}
        dialog.show(supportFragmentManager, "TaskMenuDialog")
    }
}