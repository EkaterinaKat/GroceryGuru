package com.katysh.groceryguru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityEntryEditBinding
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.viewmodel.EntryEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject


class EntryEditActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var product: ProductWithPortions? = null

    private val binding by lazy {
        ActivityEntryEditBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EntryEditViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)
        observeViewModel()

        binding.okButton.setOnClickListener { save() }
        binding.productTv.setOnClickListener { onProductTvClickListener() }

        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val product =
                    result.data?.getParcelableExtra<ProductWithPortions>(PRODUCT_RESULT_KEY)
                setProduct(product)
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

    private fun onProductTvClickListener() {
        resultLauncher.launch(Intent(this, SelectProductActivity::class.java))
    }

    private fun save() {
        viewModel.validateAndSave(
            product?.product,
            binding.weightEt.text.toString(),
            binding.datePicker.year,
            binding.datePicker.month,
            binding.datePicker.dayOfMonth
        )
    }

    private fun setProduct(product: ProductWithPortions?) {
        this.product = product
        binding.productTv.text = product?.product?.getFullInfo() ?: "null"
    }

    companion object {
        const val PRODUCT_RESULT_KEY = "product_result_key"

        fun newIntent(context: Context): Intent {
            return Intent(context, EntryEditActivity::class.java)
        }
    }
}