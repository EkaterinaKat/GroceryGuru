package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductType
import com.katysh.groceryguru.model.ProductWithPortions
import javax.inject.Inject

class ProductRepo @Inject constructor(
    private val dao: ProductDao
) {

    suspend fun add(product: Product) {
        dao.add(product)
    }

    suspend fun add(portion: Portion) {
        dao.add(portion)
    }

    suspend fun delete(portion: Portion) {
        dao.deletePortion(portion.id)
    }

    suspend fun delete(product: Product) {
        dao.deleteProduct(product.id)
    }

    suspend fun edit(product: Product) {
        dao.update(product)
    }

    suspend fun getById(id: Int): Product {
        return dao.getById(id)
    }

    suspend fun getByIdWithPortions(id: Int): ProductWithPortions {
        return dao.getProductWithPortions(id)
    }

    fun getList(): List<Product> {
        return dao.getList()
    }

    fun getListWithPortionsLd(): LiveData<List<ProductWithPortions>> {
        return dao.getListWithPortionsLD()
    }

    fun getListWithPortions(str: String?, type: ProductType?): List<ProductWithPortions> {
        var products = dao.getListWithPortions()
        if (str != null && str.trim() != "") {
            products = products.filter {
                it.product.title?.contains(str) == true
                        || it.product.desc?.contains(str) == true
            }
        }
        if (type != null) {
            products = products.filter {
                it.product.type!! == type
            }
        }
        return products

    }

    fun getListLd(): LiveData<List<Product>> {
        return dao.getListLd()
    }
}