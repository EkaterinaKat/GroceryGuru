package com.katysh.groceryguru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityProductEditBinding
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.presentation.viewmodel.ProductEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.adjustSpinner
import com.katysh.groceryguru.util.selectSpinnerItemByValue
import javax.inject.Inject

class ProductEditActivity : AppCompatActivity() {
    private var product: Product? = null

    private val binding by lazy {
        ActivityProductEditBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ProductEditViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        observeViewModel()

        binding.okButton.setOnClickListener {
            viewModel.validateAndSave(
                product,
                binding.titleEt.text.toString(),
                binding.descEt.text.toString(),
                binding.proteinEt.text.toString(),
                binding.fatEt.text.toString(),
                binding.carbEt.text.toString(),
                binding.typeSpinner.selectedItem
            )
        }

        adjustSpinner(this, binding.typeSpinner, ProductType.entries) { }

        product = intent.getParcelableExtra(PRODUCT_EXTRA)
        product?.let {
            binding.titleEt.setText(it.title)
            binding.descEt.setText(it.desc)
            binding.proteinEt.setText(it.proteins.toString())
            binding.fatEt.setText(it.fats.toString())
            binding.carbEt.setText(it.carbohydrates.toString())
            if (it.type != null) {
                selectSpinnerItemByValue(binding.typeSpinner, it.type)
            }
        }
    }

    private fun observeViewModel() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldFinishActivityLD.observe(this) {
            finish()
        }
    }

    companion object {
        private const val PRODUCT_EXTRA = "product_extra"

        fun newIntent(context: Context, product: Product?): Intent {
            return Intent(context, ProductEditActivity::class.java).apply {
                putExtra(PRODUCT_EXTRA, product)
            }
        }
    }
}