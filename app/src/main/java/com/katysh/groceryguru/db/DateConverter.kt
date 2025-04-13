package com.katysh.groceryguru.db

import androidx.room.TypeConverter
import com.katysh.groceryguru.util.removeTimeFromDate
import java.util.Date

class DateConverter {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.let { removeTimeFromDate(date).time }
    }
}