package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo,
    private val backupRepo: BackupRepo
) : ViewModel() {

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _backupLD = MutableLiveData<String>()
    val backupLD: LiveData<String>
        get() = _backupLD

    fun delete(product: Product) {
        viewModelScope.launch {
            if (expirationRepo.getByProductId(product.id).isEmpty()) {
                productRepo.delete(product)
            } else {
                _errorLD.value = Unit
            }
        }
    }

    fun backup() {
        viewModelScope.launch {
            _backupLD.value = withContext(Dispatchers.Default) {
                backupRepo.getBackup()
            }
        }

    }
}