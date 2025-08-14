package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.IngredientRepo
import com.katysh.groceryguru.model.Ingredient
import kotlinx.coroutines.launch

class IngredientViewModel(
    private val repo: IngredientRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishDialogLD = MutableLiveData<Unit>()
    val shouldFinishDialogLD: LiveData<Unit>
        get() = _shouldFinishDialogLD


    fun validateAndSaveIngredient(
        protein: String,
        fat: String,
        carbs: String,
        weight: String,
        title: String
    ) {
        try {
            if (title.trim().isEmpty()) {
                throw RuntimeException()
            }

            val proteinInt = protein.toInt()
            val fatInt = fat.toInt()
            val carbsInt = carbs.toInt()
            val weightInt = weight.toInt()
            save(proteinInt, fatInt, carbsInt, weightInt, title)
            _shouldFinishDialogLD.value = Unit
        } catch (e: Exception) {
            _errorLD.value = Unit
        }
    }

    private fun save(
        protein: Int,
        fat: Int,
        carbs: Int,
        weight: Int,
        title: String
    ) {
        val ingredient = Ingredient(
            title = title, proteins = protein, fats = fat, carbs = carbs, weight = weight
        )
        viewModelScope.launch {
            repo.add(ingredient)
        }

    }
}