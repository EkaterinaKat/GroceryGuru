package com.katysh.groceryguru.model

import androidx.room.Embedded
import androidx.room.Relation

data class ProductWithPortions(
    @Embedded val product: Product,
    @Relation(
        parentColumn = "id",
        entityColumn = "product_id"
    )
    val portions: List<Portion>
)