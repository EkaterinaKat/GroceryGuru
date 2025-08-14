package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "ingredient")
class Ingredient(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,

    val title: String? = null,

    var proteins: Int? = null,

    var fats: Int? = null,

    var carbs: Int? = null,

    val weight: Int?
) : Parcelable

