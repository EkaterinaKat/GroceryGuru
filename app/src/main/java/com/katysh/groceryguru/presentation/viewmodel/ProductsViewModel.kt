package com.katysh.groceryguru.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.util.getDateString
import com.katysh.groceryguru.util.saveTextFileToDownloads
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ProductsViewModel(
    private val productRepo: ProductRepo,
    private val backupRepo: BackupRepo,
    private val application: Application
) : ViewModel() {

    private val _productsLD = MutableLiveData<List<ProductWithPortions>>()
    val productsLD: LiveData<List<ProductWithPortions>>
        get() = _productsLD

    private val _backupResultLD = MutableLiveData<Boolean>()
    val backupResultLD: LiveData<Boolean>
        get() = _backupResultLD

    private val _selectedProductLD = MutableLiveData<ProductWithPortions>()
    val selectedProductLD: LiveData<ProductWithPortions>
        get() = _selectedProductLD

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    init {
        updateProductList(null, null)
    }

    fun backup() {
        viewModelScope.launch {
            val res = withContext(Dispatchers.Default) {
                saveTextFileToDownloads(
                    application,
                    backupRepo.getBackup(),
                    "GG-backup-" + getDateString(Date())
                )
            }
            _backupResultLD.value = res
        }
    }

    fun selectProduct(productWithPortions: ProductWithPortions) {
        _selectedProductLD.value = productWithPortions
    }

    fun validateAndSavePortion(title: String?, gram: String?) {
        if (title == null || title.trim().isEmpty()
            || gram == null || gram.trim().isEmpty()
            || _selectedProductLD.value == null
        ) {
            _errorLD.value = Unit
        } else {
            _selectedProductLD.value?.let { save(it.product, title, gram.toInt()) }
        }
    }

    private fun save(product: Product, title: String, gram: Int) {
        viewModelScope.launch {
            val portion = Portion(title = title, weight = gram, productId = product.id)
            productRepo.add(portion)
            updateSelectedProduct()
        }
    }

    fun updateSelectedProduct() {
        viewModelScope.launch {
            _selectedProductLD.value?.let {
                _selectedProductLD.value = productRepo.getByIdWithPortions(it.product.id)
            }
        }
    }

    fun delete(portion: Portion) {
        viewModelScope.launch {
            productRepo.delete(portion)
            updateSelectedProduct()
        }
    }

    fun updateProductList(str: String?, type: ProductType?) {
        viewModelScope.launch(Dispatchers.Default) {
            _productsLD.postValue(productRepo.getListWithPortions(str, type))
        }
    }
}