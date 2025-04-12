package com.katysh.groceryguru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityProductEditBinding
import com.katysh.groceryguru.presentation.viewmodel.ProductEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class ProductEditActivity : AppCompatActivity() {

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
                binding.titleEt.text.toString(),
                binding.descEt.text.toString(),
                binding.proteinEt.text.toString(),
                binding.fatEt.text.toString(),
                binding.carbEt.text.toString()
            )
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

        fun newIntent(context: Context): Intent {
            return Intent(context, ProductEditActivity::class.java)
        }
    }
}