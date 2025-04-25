package com.katysh.groceryguru.logic

import com.katysh.groceryguru.model.EntryWithProduct

data class ReportTable(
    val table: List<ReportLine>
)

data class ReportLine(
    val content: List<String>,
    val entry: EntryWithProduct? = null,
    val color: Int? = null,
    val drawable: Int? = null
)
