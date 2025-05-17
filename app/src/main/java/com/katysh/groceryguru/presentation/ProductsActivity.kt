package com.katysh.groceryguru.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityProductsBinding
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.recycleview.ProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.ProductsViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.GgActivity
import javax.inject.Inject

class ProductsActivity : GgActivity() {

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
    private var selectedType: ProductType? = null

    init {
        setOnLeftSwipe(this::finish)
        setOnStart {
            updateProductList()
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

        observeViewModel()

        binding.productsRv.layoutManager = GridLayoutManager(this, 2)
        binding.productsRv.adapter = adapter
        adapter.onClickListener = {
            productClickListener(it)
        }

        binding.backupButton.setOnClickListener { viewModel.backup() }
        binding.searchEt.addTextChangedListener(textWatcher)

        adjustTypePane()
    }

    private val textWatcher = object : TextWatcher {

        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(
            s: CharSequence, start: Int, before: Int, count: Int
        ) {
            updateProductList()
        }
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            adapter.setProducts(it)
        }
        viewModel.backupResultLD.observe(this) {
            if (it) {
                Toast.makeText(this, "Backup saved", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun productClickListener(product: ProductWithPortions) {
        viewModel.selectProduct(product)
        val dialog = ProductMenuDialog(this, viewModel) {}
        dialog.show(supportFragmentManager, "TaskMenuDialog")
    }

    private fun adjustTypePane() {
        for (type in ProductType.entries) {
            val button = Button(this)
            button.text = type.name.substring(0, 1)
            button.setBackgroundResource(type.color)
            val params = LinearLayout.LayoutParams(0, WRAP_CONTENT, 1.0f)
            button.layoutParams = params
            button.setOnClickListener {
                selectedType = type
                updateProductList()
            }
            binding.typeLayout.addView(button)
        }
    }

    private fun updateProductList() {
        viewModel.updateProductList(binding.searchEt.text.toString(), selectedType)
    }
}