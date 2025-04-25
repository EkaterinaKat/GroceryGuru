package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class EntryEditViewModel(
    private val entryRepo: EntryRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    fun validateAndSave(product: Product?, weight: String?, date: Date?) {
        if (product == null
            || weight == null || weight.trim().isEmpty()
            || date == null
        ) {
            _errorLD.value = Unit
        } else {

            save(product, weight.toInt(), date)
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
        calendar.set(year, month, dayOfMonth)
        return calendar.time
    }
}