package com.katysh.groceryguru.logic

import com.katysh.groceryguru.db.EntryDao
import com.katysh.groceryguru.db.ProductDao
import javax.inject.Inject

class BackupRepo @Inject constructor(
    private val entryDao: EntryDao,
    private val productDao: ProductDao
) {

    fun getBackup(): String {
        val dto = BackupDto(productDao.getList(), entryDao.getList())
        val gson: com.google.gson.Gson = com.google.gson.Gson()
        return gson.toJson(dto)
    }
}