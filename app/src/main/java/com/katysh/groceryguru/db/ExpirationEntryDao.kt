package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.ExpirationEntryWithProduct

@Dao
interface ExpirationEntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(expirationEntry: ExpirationEntry)

    @Query("SELECT * FROM expiration_entry ")
    fun getList(): LiveData<List<ExpirationEntryWithProduct>>

    @Query("SELECT * FROM expiration_entry WHERE id=:id ")
    suspend fun getById(id: Int): ExpirationEntryWithProduct

    @Query("DELETE FROM expiration_entry WHERE id=:id")
    suspend fun delete(id: Int)

}