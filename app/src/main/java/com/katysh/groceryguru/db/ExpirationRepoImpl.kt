package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct
import javax.inject.Inject

class ExpirationRepoImpl @Inject constructor(
    private val dao: ExpirationEntryDao
) : ExpirationRepo {

    override suspend fun add(expirationEntry: ExpirationEntry) {
        dao.add(expirationEntry)
    }

    override suspend fun delete(expirationEntry: ExpirationEntry) {
        dao.delete(expirationEntry.id)
    }

    override suspend fun edit(expirationEntry: ExpirationEntry) {
        dao.add(expirationEntry)
    }

    override suspend fun getById(id: Int): ExpirationEntryWithProduct {
        return dao.getById(id)
    }

    override fun getList(): LiveData<List<ExpirationEntryWithProduct>> {
        return dao.getList()
    }
}