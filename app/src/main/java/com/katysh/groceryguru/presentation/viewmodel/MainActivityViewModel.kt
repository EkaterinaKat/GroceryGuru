package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.CalculationRepo
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.domain.ReportRepo
import com.katysh.groceryguru.logic.DayResult
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.util.TimeUnit
import com.katysh.groceryguru.util.shiftDate
import kotlinx.coroutines.launch
import java.util.Date

class MainActivityViewModel(
    private val entryRepo: EntryRepo,
    private val calculationRepo: CalculationRepo,
    private val reportRepo: ReportRepo
) : ViewModel() {

    private val _dateLD = MutableLiveData<Date>()
    val dateLD: LiveData<Date>
        get() = _dateLD

    private val _entriesLD = MutableLiveData<List<EntryWithProduct>>()
    val entriesLD: LiveData<List<EntryWithProduct>>
        get() = _entriesLD

    private val _dayResultLD = MutableLiveData<DayResult>()
    val dayResultLD: LiveData<DayResult>
        get() = _dayResultLD

    init {
        setDate(Date())
    }

    fun delete(entry: Entry) {
        viewModelScope.launch {
            entryRepo.delete(entry)
        }
    }

    fun prevDate() {
        val newValue = shiftDate(_dateLD.value, TimeUnit.DAY, -1)
        setDate(newValue)
    }

    fun nextDate() {
        val newValue = shiftDate(_dateLD.value, TimeUnit.DAY, 1)
        setDate(newValue)
    }

    fun setDate(date: Date) {
        _dateLD.value = date
        updateEntriesList()
    }

    fun updateEntriesList() {
        _dateLD.value?.let {
            viewModelScope.launch {
                _entriesLD.value = entryRepo.getListByDate(it)
                _dayResultLD.value = calculationRepo.getDayResult(it)
            }
        }
    }
}