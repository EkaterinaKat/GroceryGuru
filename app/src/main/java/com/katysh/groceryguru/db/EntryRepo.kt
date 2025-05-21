package com.katysh.groceryguru.db

import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.MealNum
import com.katysh.groceryguru.util.equalsIgnoreTime
import java.util.Date
import javax.inject.Inject

class EntryRepo @Inject constructor(
    private val dao: EntryDao
) {

    suspend fun add(entry: Entry) {
        dao.add(entry)
    }

    suspend fun delete(entry: Entry) {
        dao.delete(entry.id)
    }

    fun getDefaultMealNum(): MealNum {
        val entry = dao.getEntryWithMaxId()
        val lastEntryMealNum = entry?.mealNum
        if (entry == null || lastEntryMealNum == null) {
            return MealNum.MEAL_1
        }
        if (equalsIgnoreTime(entry.date, Date())) {
            return lastEntryMealNum
        }
        return MealNum.MEAL_1
    }

    suspend fun repeat(entry: Entry) {
        val newEntry = Entry(
            date = Date(),
            weight = entry.weight,
            productId = entry.productId,
            mealNum = entry.mealNum
        )
        add(newEntry)
    }

    suspend fun update(entry: Entry) {
        dao.update(entry)
    }
}