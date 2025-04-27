package com.katysh.groceryguru.db

import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.model.MealNum
import com.katysh.groceryguru.util.equalsIgnoreTime
import com.katysh.groceryguru.util.removeTimeFromDate
import java.util.Date
import javax.inject.Inject

class EntryRepoImpl @Inject constructor(
    private val dao: EntryDao
) : EntryRepo {

    override suspend fun add(entry: Entry) {
        dao.add(entry)
    }

    override suspend fun delete(entry: Entry) {
        dao.delete(entry.id)
    }

    override suspend fun getListByDate(date: Date): List<EntryWithProduct> {
        return dao.getEntriesByDate(removeTimeFromDate(date))
    }

    override suspend fun getDefaultMealNum(): MealNum {
        val entry = dao.getEntryWithMaxId()
        if (entry == null || entry.mealNum == null) {
            return MealNum.MEAL_1
        }
        if (equalsIgnoreTime(entry.date, Date())) {
            return entry.mealNum
        }
        return MealNum.MEAL_1
    }
}