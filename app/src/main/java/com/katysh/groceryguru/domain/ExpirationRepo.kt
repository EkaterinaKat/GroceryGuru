package com.katysh.groceryguru.domain

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct

interface ExpirationRepo {

    suspend fun add(expirationEntry: ExpirationEntry)

    suspend fun delete(expirationEntry: ExpirationEntry)

    suspend fun edit(expirationEntry: ExpirationEntry)

    suspend fun getById(id: Int): ExpirationEntryWithProduct

    fun getList(): LiveData<List<ExpirationEntryWithProduct>>
}