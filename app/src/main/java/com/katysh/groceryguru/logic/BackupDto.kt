package com.katysh.groceryguru.logic

import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.Product

data class BackupDto(
    val products: List<Product>,
    val entries: List<Entry>
)
