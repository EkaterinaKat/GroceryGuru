package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.launch

class ProductEditViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    fun validateAndSave(string: String?) {
        if (string == null || string.trim().isEmpty()) {
            _errorLD.value = Unit
        } else {
            save(string)
        }
    }

    private fun save(string: String) {
        viewModelScope.launch {
            val product = Product(0, string)
            productRepo.add(product)
            _shouldFinishActivityLD.value = Unit
        }
    }

}