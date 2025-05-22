package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.ProductRepo
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.model.ProductWithPortions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _productsLD = MutableLiveData<List<ProductWithPortions>>()
    val productsLD: LiveData<List<ProductWithPortions>>
        get() = _productsLD

    fun updateProductList(str: String?, type: ProductType?, archived: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            _productsLD.postValue(productRepo.getListWithPortions(str, type, archived))
        }
    }
}