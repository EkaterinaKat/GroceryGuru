package com.katysh.groceryguru.model

import androidx.room.Embedded
import androidx.room.Relation

data class EntryWithProduct(
    @Embedded
    val entry: Entry,

    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: Product
) {

    fun getInfo(): String {
        return product.title + " " + entry.weight + " "
    }

    fun getInfoForReport(): String {
        return "${product.title} ${entry.weight}\n${product.proteins} ${product.fats} ${product.carbohydrates}"
    }
}