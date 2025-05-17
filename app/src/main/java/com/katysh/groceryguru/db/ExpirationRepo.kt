package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct
import javax.inject.Inject

class ExpirationRepo @Inject constructor(
    private val dao: ExpirationEntryDao
) {

    suspend fun add(expirationEntry: ExpirationEntry) {
        dao.add(expirationEntry)
    }

    suspend fun delete(expirationEntry: ExpirationEntry) {
        dao.delete(expirationEntry.id)
    }

    suspend fun edit(expirationEntry: ExpirationEntry) {
        dao.add(expirationEntry)
    }

    suspend fun getById(id: Int): ExpirationEntryWithProduct {
        return dao.getById(id)
    }

    fun getList(): LiveData<List<ExpirationEntryWithProduct>> {
        return dao.getList()
    }

    suspend fun getByProductId(id: Int): List<ExpirationEntryWithProduct> {
        return dao.getByProductId(id)
    }
}