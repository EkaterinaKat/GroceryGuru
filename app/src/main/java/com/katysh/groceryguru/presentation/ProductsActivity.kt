package com.katysh.groceryguru.presentation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.R
import com.katysh.groceryguru.databinding.ActivityProductsBinding
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.GgActivity
import javax.inject.Inject

class ProductsActivity : GgActivity(), ProductListFragment.OnProductClickListener {

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

    private val listFragment = ProductListFragment.newInstance()

    init {
        setOnLeftSwipe(this::finish)
        setOnStart {
            listFragment.updateProductList()
            viewModel.updateSelectedProduct()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        binding.addButton.setOnClickListener {
            startActivity(ProductEditActivity.newIntent(this, null))
        }
        binding.backupButton.setOnClickListener { viewModel.backup() }
        binding.calculatorButton.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }
        binding.dividerButton.setOnClickListener {
            startActivity(Intent(this, DividerActivity::class.java))
        }

        observeViewModel()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, listFragment)
            .commit()

        binding.archiveCB.setOnClickListener {
            listFragment.showArchived = binding.archiveCB.isChecked
        }
    }

    private fun observeViewModel() {
        viewModel.backupResultLD.observe(this) {
            if (it) {
                Toast.makeText(this, "Backup saved", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onProductClick(product: ProductWithPortions) {
        viewModel.selectProduct(product)
        val dialog = ProductMenuDialog(this, viewModel) { listFragment.updateProductList() }
        dialog.show(supportFragmentManager, "TaskMenuDialog")
    }
}