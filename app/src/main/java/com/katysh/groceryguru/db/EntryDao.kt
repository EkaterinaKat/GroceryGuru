package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct

@Dao
interface EntryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(entry: Entry)

    @Query("DELETE FROM entry WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM entry ")
    fun getList(): LiveData<List<EntryWithProduct>>
}