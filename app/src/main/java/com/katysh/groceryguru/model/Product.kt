package com.katysh.groceryguru.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID

@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,
    val title: String? = null,
    val desc: String? = null,
    val proteins: Int? = null,
    val fats: Int? = null,
    val carbohydrates: Int? = null
) {
    override fun toString(): String {
        return title ?: "no title :((("
    }

    fun getFullInfo(): String {
        return "$title\n$desc\nБ $proteins Ж $fats У $carbohydrates"
    }

    fun getInfoForReport(): String {
        return "$title\n$proteins $fats $carbohydrates"
    }
}


