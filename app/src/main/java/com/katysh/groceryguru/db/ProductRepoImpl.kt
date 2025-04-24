package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.Product
import javax.inject.Inject

class ProductRepoImpl @Inject constructor(
    private val dao: ProductDao
) : ProductRepo {

    override suspend fun add(product: Product) {
        dao.add(product)
    }

    override suspend fun add(portion: Portion) {
        dao.add(portion)
    }

    override suspend fun delete(product: Product) {
        dao.delete(product.id)
    }

    override suspend fun edit(product: Product) {
        dao.update(product)
    }

    override suspend fun getById(id: Int): Product {
        return dao.getById(id)
    }

    override fun getList(): List<Product> {
        return dao.getList()
    }

    override fun getListLd(): LiveData<List<Product>> {
        return dao.getListLd()
    }
}