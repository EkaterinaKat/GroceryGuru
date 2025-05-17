package com.katysh.groceryguru.model

import com.katysh.groceryguru.R

enum class ProductType(
    val num: Int,
    val color: Int
) {
    OTHER(1, R.color.other),
    MEAT(2, R.color.meat),
    GARNISH(3, R.color.garnish),
    SWEETS(4, R.color.sweets),
    SNACK(5, R.color.snack),
    BF(6, R.color.bf);

    companion object {
        fun getByNum(int: Int): ProductType {
            for (type in ProductType.entries) {
                if (int == type.num) {
                    return type
                }
            }
            throw RuntimeException()
        }
    }
}