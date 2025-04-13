package com.katysh.groceryguru.domain

import com.katysh.groceryguru.logic.DayResult
import java.util.Date

interface CalculationRepo {

    suspend fun getDayResult(date: Date): DayResult
}