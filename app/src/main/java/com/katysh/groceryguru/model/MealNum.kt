package com.katysh.groceryguru.model

import com.katysh.groceryguru.R

enum class MealNum(
    val num: Int,
    val color: Int
) {
    MEAL_1(1, R.color.meal_1),
    MEAL_2(2, R.color.meal_2),
    MEAL_3(3, R.color.meal_3),
    MEAL_4(4, R.color.meal_4),
    MEAL_5(5, R.color.meal_5);

    companion object {
        fun getByNum(int: Int): MealNum {
            for (meal in MealNum.entries) {
                if (int == meal.num) {
                    return meal
                }
            }
            throw RuntimeException()
        }
    }
}