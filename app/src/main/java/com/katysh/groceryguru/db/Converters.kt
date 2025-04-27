package com.katysh.groceryguru.db

import androidx.room.TypeConverter
import com.katysh.groceryguru.model.MealNum
import com.katysh.groceryguru.util.removeTimeFromDate
import java.util.Date

class Converters {
    @TypeConverter
    fun toDate(timestamp: Long?): Date? {
        return timestamp?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(date: Date?): Long? {
        return date?.let { removeTimeFromDate(date).time }
    }

    @TypeConverter
    fun intToMeal(value: Int?): MealNum? {
        return if (value == null) null else MealNum.getByNum(value)
    }

    @TypeConverter
    fun mealToInt(mealNum: MealNum?): Int? {
        return mealNum?.num
    }
}