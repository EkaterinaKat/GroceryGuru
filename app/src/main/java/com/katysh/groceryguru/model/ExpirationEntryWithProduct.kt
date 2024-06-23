package com.katysh.groceryguru.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExpirationEntryWithProduct(
    @Embedded val expirationEntry: ExpirationEntry,
    @Relation(
        parentColumn = "product_id",
        entityColumn = "id"
    )
    val product: Product
)