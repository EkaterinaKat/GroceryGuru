package com.katysh.groceryguru.domain

import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import java.util.Date

interface EntryRepo {

    suspend fun add(entry: Entry)

    suspend fun delete(entry: Entry)

    suspend fun getListByDate(date: Date): List<EntryWithProduct>
}