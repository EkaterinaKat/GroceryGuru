package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Product

class ProductsViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

}