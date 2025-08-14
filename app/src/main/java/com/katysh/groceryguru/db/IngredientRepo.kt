package com.katysh.groceryguru.db

import com.katysh.groceryguru.R
import com.katysh.groceryguru.model.Ingredient
import com.katysh.groceryguru.model.Pfc
import javax.inject.Inject
import kotlin.math.roundToInt

class IngredientRepo @Inject constructor(
    private val dao: IngredientDao
) {

    suspend fun deleteAll() {
        dao.deleteAll()
    }

    suspend fun calculatePortion(portions: Int): Pfc {
        val total = calculatePfcTotal()
        return Pfc(
            (total.protein / portions.toDouble()).roundToInt(),
            (total.fat / portions.toDouble()).roundToInt(),
            (total.carb / portions.toDouble()).roundToInt()
        )
    }

    suspend fun add(ingredient: Ingredient) {
        dao.add(ingredient)
    }

    suspend fun delete(ingredient: Ingredient) {
        dao.delete(ingredient.id)
    }

    suspend fun calculatePfcTotal(): Pfc {

        var proteinTotal = 0f
        var fatTotal = 0f
        var carbTotal = 0f

        for (ingredient in dao.getList()) {
            val weight = ingredient.weight!!
            proteinTotal += ingredient.proteins!!.toFloat() * weight / 100
            fatTotal += ingredient.fats!!.toFloat() * weight / 100
            carbTotal += ingredient.carbs!!.toFloat() * weight / 100
        }

        return Pfc(proteinTotal.toInt(), fatTotal.toInt(), carbTotal.toInt())
    }

    suspend fun getTable(): IngredientTable {
        val table = mutableListOf<IngredientLine>()
        table.add(IngredientLine(content = listOf("", "Б", "Ж", "У")))
        val entryLines = mutableListOf<IngredientLine>()

        val ingredients = dao.getList()
        var proteinTotal = 0f
        var fatTotal = 0f
        var carbTotal = 0f

        for (ingredient in ingredients) {
            val weight = ingredient.weight!!
            val protein = ingredient.proteins!!.toFloat() * weight / 100
            val fat = ingredient.fats!!.toFloat() * weight / 100
            val carbs = ingredient.carbs!!.toFloat() * weight / 100

            val text = "${ingredient.title} ${ingredient.weight}"

            entryLines.add(
                IngredientLine(
                    content = listOf(
                        text,
                        "${formatNum(protein)} \n(${ingredient.proteins})",
                        "${formatNum(fat)} \n(${ingredient.fats})",
                        "${formatNum(carbs)} \n(${ingredient.carbs})"
                    ),
                    ingredient = ingredient
                )
            )

            proteinTotal += protein
            fatTotal += fat
            carbTotal += carbs
        }

        table.add(
            IngredientLine(
                content = listOf(
                    "Total",
                    formatNum(proteinTotal),
                    formatNum(fatTotal),
                    formatNum(carbTotal)
                ),
                drawable = R.drawable.blue_gradient
            )
        )

        table.addAll(entryLines)

        return IngredientTable(table)
    }

    private fun formatNum(num: Float): String {
        return String.format("%.0f", num)
    }

    data class IngredientTable(
        val table: List<IngredientLine>
    )

    data class IngredientLine(
        val content: List<String>,
        val ingredient: Ingredient? = null,
        val color: Int? = null,
        val drawable: Int? = null
    )
}