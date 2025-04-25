package com.katysh.groceryguru.presentation

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityEntryEditBinding
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.viewmodel.EntryEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.getDateByString
import com.katysh.groceryguru.util.getDateString
import java.util.Date
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
        binding.dateTextView.text = getDateString(Date())
        binding.dateTextView.setOnClickListener(View.OnClickListener { view: View? ->
            openDatePicker()
        })

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

    fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { dp: DatePicker?, year: Int, month: Int, day: Int ->
            binding.dateTextView.text = getDateString(year, month + 1, day)
        }
        datePickerDialog.show()
    }

    private fun onProductTvClickListener() {
        resultLauncher.launch(Intent(this, SelectProductActivity::class.java))
    }

    private fun save() {
        viewModel.validateAndSave(
            product?.product,
            binding.weightEt.text.toString(),
            getDateByString(binding.dateTextView.text.toString())
        )
    }

    private fun setProduct(product: ProductWithPortions?) {
        this.product = product

        product?.let {
            binding.productTv.text = it.product.getFullInfo()
            fillPortionLayout(it.portions)
        }


    }

    private fun fillPortionLayout(portions: List<Portion>) {
        binding.portionLayout.removeAllViews()
        for (portion in portions) {

            val textView = Button(this).apply {
                text = "${portion.title} ${portion.weight}"
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    gravity = Gravity.CENTER
                }
                setOnClickListener { onPortionClickListener(portion) }
            }
            binding.portionLayout.addView(textView)
        }
    }

    private fun onPortionClickListener(portion: Portion) {
        binding.weightEt.setText(portion.weight.toString())
    }

    companion object {
        const val PRODUCT_RESULT_KEY = "product_result_key"

        fun newIntent(context: Context): Intent {
            return Intent(context, EntryEditActivity::class.java)
        }
    }
}