package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
@Entity(
    tableName = "entry",
    foreignKeys = [
        ForeignKey(
            entity = Product::class,
            parentColumns = ["id"],
            childColumns = ["product_id"],
            onDelete = ForeignKey.RESTRICT
        )
    ]
)
class Entry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,

    @ColumnInfo(name = "entry_date")
    var date: Date?,

    var weight: Int?,

    @ColumnInfo(name = "product_id")
    var productId: Int,

    var mealNum: MealNum?
) : Parcelable