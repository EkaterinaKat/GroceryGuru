package com.katysh.groceryguru.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.katysh.groceryguru.model.Portion
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductWithPortions

@Dao
interface ProductDao {

    @Query("SELECT * FROM product ORDER BY title")
    fun getList(): List<Product>

    @Query("SELECT * FROM product ORDER BY title")
    fun getListWithPortionsLD(): LiveData<List<ProductWithPortions>>

    @Query("SELECT * FROM product ORDER BY title")
    fun getListWithPortions(): List<ProductWithPortions>

    @Query("SELECT * FROM product ORDER BY title")
    fun getListLd(): LiveData<List<Product>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(product: Product)

    @Update
    suspend fun update(product: Product): Int

    @Query("DELETE FROM product WHERE id=:id")
    suspend fun deleteProduct(id: Int)

    @Query("DELETE FROM portion WHERE id=:id")
    suspend fun deletePortion(id: Int)

    @Query("SELECT * FROM product WHERE id=:id LIMIT 1")
    suspend fun getById(id: Int): Product

    @Query("SELECT * FROM product WHERE id=:id LIMIT 1")
    suspend fun getProductWithPortions(id: Int): ProductWithPortions

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(portion: Portion)
}




