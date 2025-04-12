package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import kotlinx.coroutines.launch

class EntryViewModel(
    private val entryRepo: EntryRepo
) : ViewModel() {

    val entriesLD: LiveData<List<EntryWithProduct>>
        get() = entryRepo.getList()

    fun delete(entry: Entry) {
        viewModelScope.launch {
            entryRepo.delete(entry)
        }
    }
}