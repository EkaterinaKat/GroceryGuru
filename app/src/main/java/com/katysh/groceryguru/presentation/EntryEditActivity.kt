package com.katysh.groceryguru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityEntryEditBinding
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.presentation.viewmodel.EntryEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.adjustSpinner
import javax.inject.Inject

class EntryEditActivity : AppCompatActivity() {
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

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            adjustSpinner(this, binding.spinner, it, null)
        }
        viewModel.errorLD.observe(this) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        viewModel.shouldFinishActivityLD.observe(this) {
            finish()
        }
    }

    private fun save() {
        viewModel.validateAndSave(
            binding.spinner.selectedItem as Product,
            binding.weightEt.text.toString(),
            binding.datePicker.year,
            binding.datePicker.month,
            binding.datePicker.dayOfMonth
        )
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, EntryEditActivity::class.java)
        }
    }
}