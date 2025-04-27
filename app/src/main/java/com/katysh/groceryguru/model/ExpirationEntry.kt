package com.katysh.groceryguru.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.katysh.groceryguru.db.Converters
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import java.util.Date

@Entity(tableName = "expiration_entry")
data class ExpirationEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,

    @ColumnInfo(name = "expiration_date")
    @TypeConverters(Converters::class)
    val expirationDate: Date?,

    @ColumnInfo(name = "start_date")
    @TypeConverters(Converters::class)
    val startDate: Date?,

    @ColumnInfo(name = "product_id")
    val productId: Int,

    val comment: String? = null
)