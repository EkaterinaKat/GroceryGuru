package com.katysh.groceryguru.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityCalculatorBinding
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.presentation.recycleview.CalculatorProductAdapter
import com.katysh.groceryguru.presentation.viewmodel.CalculatorViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.adjustSpinner
import javax.inject.Inject

class CalculatorActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityCalculatorBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[CalculatorViewModel::class.java]
    }

    private val adapter = CalculatorProductAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)
        observeViewModel()

        binding.ingredientsRv.layoutManager = LinearLayoutManager(this)
        adapter.onClickListener = { ingredient ->
            val dlg1: DialogFragment = QuestionDialog("Delete?") { answer ->
                if (answer) {
                    viewModel.delete(ingredient)
                }
            }
            dlg1.show(supportFragmentManager, "dialog")
        }
        binding.ingredientsRv.adapter = adapter

        binding.addIngredientButton.setOnClickListener {
            val dialog = IngredientDialog(this, viewModelFactory) {
                viewModel.updateList()
            }
            dialog.show(supportFragmentManager, "")
        }

        binding.portionsEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.calculate(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.saveButton.setOnClickListener {
            viewModel.saveProduct(
                binding.productTitleEt.text.toString(),
                binding.typeSpinner.selectedItem
            )
        }

        adjustSpinner(this, binding.typeSpinner, ProductType.entries) { }
    }

    fun observeViewModel() {
        viewModel.errorLD.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.ingListLD.observe(this) {
            adapter.setData(it)
        }
        viewModel.portionPfcLD.observe(this) {
            binding.proteinTv.text = it.protein.toString()
            binding.fatTv.text = it.fat.toString()
            binding.carbTv.text = it.carb.toString()
        }
        viewModel.shouldFinishActivityLD.observe(this) {
            finish()
        }
    }
}