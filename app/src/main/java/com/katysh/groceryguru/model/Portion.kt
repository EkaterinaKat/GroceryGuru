package com.katysh.groceryguru.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(
    tableName = "portion",
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["product_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("product_id")] // не поняла зачем но нейронка говорит что это топ
)
data class Portion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,

    val title: String? = null,

    val weight: Int?,

    @ColumnInfo(name = "product_id")
    val productId: Int
) : Parcelable