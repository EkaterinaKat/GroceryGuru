package com.katysh.groceryguru.logic

import com.katysh.groceryguru.model.EntryWithProduct

data class ReportTable(
    val table: List<ReportLine>
)

data class ReportLine(
    val content: List<String>,
    var entry: EntryWithProduct?=null,
    var color: Int?=null
)
