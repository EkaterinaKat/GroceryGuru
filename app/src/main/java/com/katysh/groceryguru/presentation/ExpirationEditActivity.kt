package com.katysh.groceryguru.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityExpirationEditBinding
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.presentation.viewmodel.ExpirationEditViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.DatePicker
import com.katysh.groceryguru.util.adjustSpinner
import javax.inject.Inject

class ExpirationEditActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityExpirationEditBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ExpirationEditViewModel::class.java]
    }

    private lateinit var startDp: DatePicker
    private lateinit var endDp: DatePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        observeViewModel()

        binding.okButton.setOnClickListener { save() }

        startDp = DatePicker(this, binding.startTv, null)
        endDp = DatePicker(this, binding.endTv, null)

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.productsLD.observe(this) {
            Log.i("tag6483", "viewModel.productsLD.observe $it")
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
            binding.commentEt.text.toString(),
            startDp.selectedDate,
            endDp.selectedDate
        )
    }

    companion object {

        fun newIntent(context: Context): Intent {
            return Intent(context, ExpirationEditActivity::class.java)
        }
    }
}