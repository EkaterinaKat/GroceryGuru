package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class EntryEditViewModel(
    private val productRepo: ProductRepo,
    private val entryRepo: EntryRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

    fun validateAndSave(product: Product?, weight: String?, year: Int?, month: Int?, day: Int?) {
        if (product == null || weight == null || year == null || month == null || day == null) {
            _errorLD.value = Unit
        } else {

            save(product, weight.toInt(), convertToDate(year, month, day))
        }
    }

    private fun save(product: Product, weight: Int, date: Date) {
        viewModelScope.launch {
            val entry = Entry(
                productId = product.id,
                weight = weight,
                date = date
            )
            entryRepo.add(entry)
            _shouldFinishActivityLD.value = Unit
        }
    }

    private fun convertToDate(year: Int, month: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, dayOfMonth)
        return calendar.time
    }
}