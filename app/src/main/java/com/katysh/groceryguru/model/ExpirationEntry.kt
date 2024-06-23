package com.katysh.groceryguru.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.katysh.groceryguru.db.DateConverter
import com.katysh.groceryguru.util.UNSPECIFIED_ID
import com.katysh.groceryguru.util.getDateString
import com.katysh.groceryguru.util.isNotBlank
import java.util.Date

@Entity(tableName = "expiration_entry")
data class ExpirationEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNSPECIFIED_ID,

    @ColumnInfo(name = "expiration_date")
    @TypeConverters(DateConverter::class)
    val expirationDate: Date?,

    @ColumnInfo(name = "start_date")
    @TypeConverters(DateConverter::class)
    val startDate: Date?,

    @ColumnInfo(name = "product_id")
    val productId: Int,

    val comment: String? = null
) {

    fun getInfo(): String {
        val builder = StringBuilder()

        if (isNotBlank(comment)) {
            builder.append(comment).append("\n\n")
        }

        builder
            .append(getDateString(startDate))
            .append(" - ")
            .append(getDateString(expirationDate))

        return builder.toString()
    }
}