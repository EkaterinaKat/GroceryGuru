package com.katysh.groceryguru.logic

import java.util.Date

data class DayResult(
    val date: Date,
    val proteinTotal: Int,
    val fatTotal: Int,
    val carbonTotal: Int
)