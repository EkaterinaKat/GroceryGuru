package com.katysh.groceryguru.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.katysh.groceryguru.model.Ingredient

@Dao
interface IngredientDao {

    @Query("SELECT * FROM ingredient ")
    suspend fun getList(): List<Ingredient>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun add(ingredient: Ingredient)

    @Query("DELETE FROM ingredient WHERE id=:id")
    suspend fun delete(id: Int)

    @Query("DELETE FROM ingredient")
    suspend fun deleteAll()
}