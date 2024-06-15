package com.katysh.groceryguru.domain

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.model.Product

interface ProductRepo {

    suspend fun add(product: Product)

    suspend fun delete(product: Product)

    suspend fun edit(product: Product)

    suspend fun getById(id: Int): Product

    fun getList(): LiveData<List<Product>>
}