package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.launch

class ProductsViewModel(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo
) : ViewModel() {

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    fun delete(product: Product) {
        viewModelScope.launch {
            if (expirationRepo.getByProductId(product.id).isEmpty()) {
                productRepo.delete(product)
            } else {
                _errorLD.value = Unit
            }
        }
    }

}