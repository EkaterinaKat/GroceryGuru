package com.katysh.groceryguru.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.ProductRepo
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

    val productsLD: LiveData<List<ProductWithPortions>>
        get() = productRepo.getListWithPortionsLd()

    private val _backupResultLD = MutableLiveData<Boolean>()
    val backupResultLD: LiveData<Boolean>
        get() = _backupResultLD

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