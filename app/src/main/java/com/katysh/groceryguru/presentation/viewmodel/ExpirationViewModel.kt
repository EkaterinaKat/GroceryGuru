package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct
import kotlinx.coroutines.launch

class ExpirationViewModel(
    private val expirationRepo: ExpirationRepo
) : ViewModel() {

    val entriesLD: LiveData<List<ExpirationEntryWithProduct>>
        get() = expirationRepo.getList()

    fun delete(entry: ExpirationEntry) {
        viewModelScope.launch {
            expirationRepo.delete(entry)
        }
    }
}