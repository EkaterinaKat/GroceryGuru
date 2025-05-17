package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.EntryRepo
import com.katysh.groceryguru.logic.ReportRepo
import com.katysh.groceryguru.logic.ReportTable
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.util.TimeUnit
import com.katysh.groceryguru.util.shiftDate
import kotlinx.coroutines.launch
import java.util.Date

class MainActivityViewModel(
    private val entryRepo: EntryRepo,
    private val reportRepo: ReportRepo
) : ViewModel() {

    private val _dateLD = MutableLiveData<Date>()
    val dateLD: LiveData<Date>
        get() = _dateLD

    private val _reportLD = MutableLiveData<ReportTable>()
    val reportLD: LiveData<ReportTable>
        get() = _reportLD

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
                _reportLD.value = reportRepo.getReport(it)
            }
        }
    }
}