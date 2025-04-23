package com.katysh.groceryguru.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.util.getDateString
import com.katysh.groceryguru.util.saveTextFileToDownloads
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date

class ProductsViewModel(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo,
    private val backupRepo: BackupRepo,
    private val application: Application
) : ViewModel() {

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _backupResultLD = MutableLiveData<Boolean>()
    val backupResultLD: LiveData<Boolean>
        get() = _backupResultLD

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
}