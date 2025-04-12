package com.katysh.groceryguru.domain

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct

interface EntryRepo {

    suspend fun add(entry: Entry)

    suspend fun delete(entry: Entry)

    fun getList(): LiveData<List<EntryWithProduct>>
}