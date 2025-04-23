package com.katysh.groceryguru.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.domain.BackupRepo
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.domain.ReportRepo
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo,
    private val entryRepo: EntryRepo,
    private val backupRepo: BackupRepo,
    private val reportRepo: ReportRepo,
    private val application: Application
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ProductEditViewModel::class.java) {
            return ProductEditViewModel(productRepo) as T
        }
        if (modelClass == ProductsViewModel::class.java) {
            return ProductsViewModel(productRepo, expirationRepo, backupRepo, application) as T
        }
        if (modelClass == ExpirationEditViewModel::class.java) {
            return ExpirationEditViewModel(productRepo, expirationRepo) as T
        }
        if (modelClass == MainActivityViewModel::class.java) {
            return MainActivityViewModel(entryRepo, reportRepo) as T
        }
        if (modelClass == EntryEditViewModel::class.java) {
            return EntryEditViewModel(productRepo, entryRepo) as T
        }
        throw RuntimeException()
    }
}
