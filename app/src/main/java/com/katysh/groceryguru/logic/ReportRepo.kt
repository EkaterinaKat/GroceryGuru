package com.katysh.groceryguru.logic

import com.katysh.groceryguru.R
import com.katysh.groceryguru.db.EntryDao
import java.util.Date
import javax.inject.Inject

class ReportRepo @Inject constructor(
    private val dao: EntryDao
) {

    suspend fun getReport(date: Date): ReportTable {
        val table = mutableListOf<ReportLine>()
        table.add(ReportLine(content = listOf("", "Б", "Ж", "У")))
        val entryLines = mutableListOf<ReportLine>()

        val entries = dao.getEntriesByDate(date).sortedBy { it.entry.mealNum?.num }
        var proteinTotal = 0f
        var fatTotal = 0f
        var carbTotal = 0f

        for (entry in entries) {
            val product = entry.product.product
            val weight = entry.entry.weight!!
            val protein = product.proteins!!.toFloat() * entry.entry.weight!! / 100
            val fat = product.fats!!.toFloat() * weight / 100
            val carbs = product.carbohydrates!!.toFloat() * weight / 100

            val text = "${product.title} ${entry.entry.weight}"

            entryLines.add(
                ReportLine(
                    content = listOf(
                        text,
                        formatNum(protein),
                        formatNum(fat),
                        formatNum(carbs)
                    ),
                    entry = entry,
                    color = entry.entry.mealNum?.color
                )
            )

            proteinTotal += protein
            fatTotal += fat
            carbTotal += carbs
        }

        table.add(
            ReportLine(
                content = listOf(
                    "Total",
                    formatNum(proteinTotal),
                    formatNum(fatTotal),
                    formatNum(carbTotal)
                ),
                drawable = R.drawable.blue_gradient
            )
        )

        table.add(
            ReportLine(
                content = listOf(
                    "Norm",
                    PROTEIN_NORM.toString(),
                    FATS_NORM.toString(),
                    CARBS_NORM.toString(),
                ),
                drawable = R.drawable.grey_gradient
            )
        )

        table.add(
            ReportLine(
                content = listOf(
                    "Left",
                    formatNum(PROTEIN_NORM - proteinTotal),
                    formatNum(FATS_NORM - fatTotal),
                    formatNum(CARBS_NORM - carbTotal)
                ),
                drawable = R.drawable.orange_gradient
            )
        )

        table.addAll(entryLines)

        return ReportTable(table)
    }

    private fun formatNum(num: Float): String {
        return String.format("%.0f", num)
    }

    companion object {
        const val PROTEIN_NORM = 120
        const val FATS_NORM = 60
        const val CARBS_NORM = 150


    }
}