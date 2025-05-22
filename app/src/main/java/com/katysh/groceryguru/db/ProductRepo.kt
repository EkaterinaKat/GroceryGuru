package com.katysh.groceryguru.db

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

    suspend fun getByIdWithPortions(id: Int): ProductWithPortions {
        return dao.getProductWithPortions(id)
    }

    fun getListWithPortions(
        str: String?,
        type: ProductType?,
        archived: Boolean
    ): List<ProductWithPortions> {
        var products = dao.getListWithPortions()
            .filter { (it.product.archived == true) == archived }
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

    suspend fun archive(product: Product) {
        val archived: Boolean = product.archived == true
        product.archived = !archived
        edit(product)
    }
}