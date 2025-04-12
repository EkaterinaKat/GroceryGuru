package com.katysh.groceryguru.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.databinding.ActivityMainBinding
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.presentation.recycleview.EntryAdapter
import com.katysh.groceryguru.presentation.viewmodel.EntryViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val component by lazy {
        (application as GroceryGuruApplication).component
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[EntryViewModel::class.java]
    }

    private val adapter = EntryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        component.inject(this)

        binding.productsButton.setOnClickListener {
            startActivity(Intent(this, ProductsActivity::class.java))
        }

        binding.addEntryButton.setOnClickListener {
            startActivity(EntryEditActivity.newIntent(this))
        }

        observeViewModel()

        binding.expirationRv.adapter = adapter
        adapter.onClickListener = {
            entryClickListener(it)
        }
    }

    private fun observeViewModel() {
        viewModel.entriesLD.observe(this) {
            adapter.setEntries(it)
        }
    }

    private fun entryClickListener(entry: EntryWithProduct) {
        val dialog = EntryMenuDialog(this, entry, viewModel) {}
        dialog.show(supportFragmentManager, "TaskMenuDialog")
    }
}