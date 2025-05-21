package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class EntryWithProduct(
    @Embedded
    val entry: Entry,

    @Relation(
        entity = Product::class,
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: ProductWithPortions
) : Parcelable {

    fun getInfo(): String {
        return product.product.title + " " + entry.weight + " "
    }
}