package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import com.katysh.groceryguru.domain.EntryRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import javax.inject.Inject

class EntryRepoImpl @Inject constructor(
    private val dao: EntryDao
) : EntryRepo {

    override suspend fun add(entry: Entry) {
        dao.add(entry)
    }

    override suspend fun delete(entry: Entry) {
        dao.delete(entry.id)
    }

    override fun getList(): LiveData<List<EntryWithProduct>> {
        return dao.getList()
    }


}