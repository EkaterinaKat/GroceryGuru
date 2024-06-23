package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katysh.groceryguru.model.Product

@Dao
interface ProductDao {

    @Query("SELECT * FROM product ORDER BY title")
    fun getList(): List<Product>

    @Query("SELECT * FROM product ORDER BY title")
    fun getListLd(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(product: Product)

    @Query("DELETE FROM product WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("SELECT * FROM product WHERE id=:id LIMIT 1")
    suspend fun getById(id: Int): Product
}




