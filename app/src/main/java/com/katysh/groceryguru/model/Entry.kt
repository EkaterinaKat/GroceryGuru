package com.katysh.groceryguru.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.katysh.groceryguru.db.DateConverter
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import java.util.Date

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
    @TypeConverters(DateConverter::class)
    val date: Date?,

    val weight: Int?,

    @ColumnInfo(name = "product_id")
    val productId: Int
)