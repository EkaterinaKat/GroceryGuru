package com.katysh.groceryguru.domain

import com.katysh.groceryguru.logic.ReportTable
import java.util.Date

interface ReportRepo {

    suspend fun getReport(date: Date): ReportTable
}