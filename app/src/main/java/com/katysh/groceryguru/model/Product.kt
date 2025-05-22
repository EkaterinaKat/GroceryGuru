package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "product")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,
    var title: String? = null,
    var desc: String? = null,
    var proteins: Int? = null,
    var fats: Int? = null,
    var carbohydrates: Int? = null,
    var type: ProductType? = null,
    var archived: Boolean? = null
) : Parcelable {

    fun getFullInfo(): String {
        return "$title\n$desc\n${getNutrientsInfo()}"
    }

    private fun getNutrientsInfo(): String {
        return "$proteins - $fats - $carbohydrates"
    }
}


