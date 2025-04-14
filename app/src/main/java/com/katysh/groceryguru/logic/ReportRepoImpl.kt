package com.katysh.groceryguru.logic

import com.katysh.groceryguru.db.EntryDao
import com.katysh.groceryguru.domain.ReportRepo
import java.util.Date
import javax.inject.Inject

class ReportRepoImpl @Inject constructor(
    private val dao: EntryDao
) : ReportRepo {

    override suspend fun getReport(date: Date): ReportTable {
        val table = mutableListOf<List<String>>()
        table.add(listOf("", "Б", "Ж", "У"))

        val entries = dao.getEntriesByDate(date)
        var proteinTotal = 0f
        var fatTotal = 0f
        var carbTotal = 0f

        for (entry in entries) {
            val protein = entry.product.proteins!!.toFloat() * entry.entry.weight!! / 100
            val fat = entry.product.fats!!.toFloat() * entry.entry.weight / 100
            val carbs = entry.product.carbohydrates!!.toFloat() * entry.entry.weight / 100

            table.add(
                listOf(
                    entry.product.getInfoForReport(),
                    protein.toString(),
                    fat.toString(),
                    carbs.toString(),
                )
            )

            proteinTotal += protein
            fatTotal += fat
            carbTotal += carbs
        }

        table.add(
            listOf(
                "Total",
                proteinTotal.toString(),
                fatTotal.toString(),
                carbTotal.toString(),
            )
        )

        table.add(
            listOf(
                "Norm",
                PROTEIN_NORM.toString(),
                FATS_NORM.toString(),
                CARBS_NORM.toString(),
            )
        )

        table.add(
            listOf(
                "Left",
                (PROTEIN_NORM - proteinTotal).toString(),
                (FATS_NORM - fatTotal).toString(),
                (CARBS_NORM - carbTotal).toString()
            )
        )

        return ReportTable(table)
    }

    companion object {
        const val PROTEIN_NORM = 100
        const val FATS_NORM = 60
        const val CARBS_NORM = 150


    }
}