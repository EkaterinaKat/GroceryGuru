package com.katysh.groceryguru.presentation

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.GroceryGuruApplication
import com.katysh.groceryguru.R
import com.katysh.groceryguru.databinding.ActivityMainBinding
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.presentation.recycleview.EntryAdapter
import com.katysh.groceryguru.presentation.recycleview.ReportAdapter
import com.katysh.groceryguru.presentation.viewmodel.MainActivityViewModel
import com.katysh.groceryguru.presentation.viewmodel.ViewModelFactory
import com.katysh.groceryguru.util.equalsIgnoreTime
import com.katysh.groceryguru.util.getDateStringWithWeekDay
import com.katysh.groceryguru.util.parse
import java.util.Date
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
        ViewModelProvider(this, viewModelFactory)[MainActivityViewModel::class.java]
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

        adapter.onClickListener = {
            entryClickListener(it)
        }

        binding.prevButton.setOnClickListener { viewModel.prevDate() }
        binding.nextButton.setOnClickListener { viewModel.nextDate() }

        binding.mainDateTextView.setOnClickListener { openDatePicker() }
    }

    override fun onResume() {
        super.onResume()
        viewModel.updateEntriesList()
    }

    private fun observeViewModel() {
        viewModel.dateLD.observe(this) {
            binding.mainDateTextView.text = getDateStringWithWeekDay(it)
            setDateViewStyle(it)
        }
        viewModel.reportLD.observe(this) {

            val adapter = ReportAdapter(this, it.table)
            binding.gridView.adapter = adapter

            binding.gridView.setOnItemClickListener { _, view, position, _ ->
                Toast.makeText(this, "Clicked: ${it.table[position]}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun entryClickListener(entry: EntryWithProduct) {
        val dialog = EntryMenuDialog(this, entry, viewModel) {}
        dialog.show(supportFragmentManager, "TaskMenuDialog")
    }

    private fun setDateViewStyle(date: Date) {
        if (equalsIgnoreTime(date, Date())) {
            binding.mainDateTextView.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
            binding.mainDateTextView.setTypeface(null, Typeface.BOLD)
        } else {
            binding.mainDateTextView.setTextColor(ContextCompat.getColor(this, R.color.black))
            binding.mainDateTextView.setTypeface(null, Typeface.NORMAL)
        }
    }

    private fun openDatePicker() {
        val datePickerDialog = DatePickerDialog(this)
        datePickerDialog.setOnDateSetListener { _: DatePicker?, year: Int, month: Int, day: Int ->
            val date = parse(year, month + 1, day)
            viewModel.setDate(date)
        }
        datePickerDialog.show()
    }
}