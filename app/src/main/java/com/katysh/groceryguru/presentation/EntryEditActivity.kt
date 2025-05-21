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
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityEntryEditBinding
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.model.MealNum
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.viewmodel.EntryEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.GgActivity
import com.katysh.groceryguru.util.getDateByString
import com.katysh.groceryguru.util.getDateString
import java.util.Date
import javax.inject.Inject


class EntryEditActivity : GgActivity() {
    private var existingEntry: EntryWithProduct? = null
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private var product: ProductWithPortions? = null
    private var selectedMealNum: MealNum? = null

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

    init {
        setOnLeftSwipe(this::finish)
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

        existingEntry = intent.getParcelableExtra<EntryWithProduct>(ENTRY_KEY)
        existingEntry?.let {
            setProduct(it.product)
            binding.weightEt.setText(it.entry.weight.toString())
            binding.dateTextView.text = getDateString(it.entry.date)
            selectMealNum(it.entry.mealNum)
        }
    }

    private fun observeViewModel() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldFinishActivityLD.observe(this) {
            finish()
        }
        viewModel.defaultMealNumLD.observe(this) {
            if (selectedMealNum == null) {
                selectMealNum(it)
            }
        }
    }

    private fun selectMealNum(mealNum: MealNum?) {
        selectedMealNum = mealNum
        binding.mealNumLayout.removeAllViews()
        for (mn in MealNum.entries) {
            val button = Button(this).apply {
                val lp = LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                lp.weight = 1f

                layoutParams = lp
                text = mn.num.toString()
                if (mn == selectedMealNum) {
                    setBackgroundColor(resources.getColor(mn.color))
                }
                setOnClickListener { selectMealNum(mn) }
            }

            binding.mealNumLayout.addView(button)
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
            existingEntry,
            product?.product,
            binding.weightEt.text.toString(),
            getDateByString(binding.dateTextView.text.toString()),
            selectedMealNum
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
        const val ENTRY_KEY = "entry_key"

        fun newIntent(context: Context, entryWithProduct: EntryWithProduct?): Intent {
            return Intent(context, EntryEditActivity::class.java).apply {
                putExtra(ENTRY_KEY, entryWithProduct)
            }
        }
    }
}