package com.katysh.groceryguru.logic

import com.katysh.groceryguru.db.EntryDao
import com.katysh.groceryguru.domain.CalculationRepo
import java.util.Date
import javax.inject.Inject

class CalculationRepoImpl @Inject constructor(
    private val dao: EntryDao
) : CalculationRepo {

    override suspend fun getDayResult(date: Date): DayResult {
        val entries = dao.getEntriesByDate(date)
        var proteinTotal = 0f
        var fatTotal = 0f
        var carbTotal = 0f

        for (entry in entries) {
            proteinTotal += entry.product.proteins!!.toFloat() * entry.entry.weight!! / 100
            fatTotal += entry.product.fats!!.toFloat() * entry.entry.weight / 100
            carbTotal += entry.product.carbohydrates!!.toFloat() * entry.entry.weight / 100
        }

        return DayResult(date, proteinTotal.toInt(), fatTotal.toInt(), carbTotal.toInt())
    }
}