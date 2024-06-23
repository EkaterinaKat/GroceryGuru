package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo
) : ViewModelProvider.Factory {


    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass == ProductEditViewModel::class.java) {
            return ProductEditViewModel(productRepo) as T
        }
        if (modelClass == ProductsViewModel::class.java) {
            return ProductsViewModel(productRepo, expirationRepo) as T
        }
        if (modelClass == ExpirationViewModel::class.java) {
            return ExpirationViewModel(expirationRepo) as T
        }
        if (modelClass == ExpirationEditViewModel::class.java) {
            return ExpirationEditViewModel(productRepo, expirationRepo) as T
        }
        throw RuntimeException()
    }
}
