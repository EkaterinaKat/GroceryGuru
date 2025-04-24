package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Relation
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductWithPortions(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "product_id"
    )
    val portions: List<Portion>
) : Parcelable