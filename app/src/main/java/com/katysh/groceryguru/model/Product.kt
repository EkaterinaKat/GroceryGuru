package com.katysh.groceryguru.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,
    val title: String? = null
) {
    override fun toString(): String {
        return title ?: "no title :((("
    }
}


