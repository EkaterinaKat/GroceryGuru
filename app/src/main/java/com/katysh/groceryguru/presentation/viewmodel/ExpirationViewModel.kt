package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.model.ExpirationEntryWithProduct

class ExpirationViewModel(
    private val expirationRepo: ExpirationRepo
) : ViewModel() {

    val entriesLD: LiveData<List<ExpirationEntryWithProduct>>
        get() = expirationRepo.getList()
}