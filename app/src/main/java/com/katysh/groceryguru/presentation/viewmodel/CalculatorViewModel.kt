package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.IngredientRepo
import com.katysh.groceryguru.db.IngredientRepo.IngredientTable
import com.katysh.groceryguru.db.ProductRepo
import com.katysh.groceryguru.model.Ingredient
import com.katysh.groceryguru.model.Pfc
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductType
import kotlinx.coroutines.launch

class CalculatorViewModel(
    private val repo: IngredientRepo,
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _ingListLD = MutableLiveData<IngredientTable>()
    val ingListLD: LiveData<IngredientTable>
        get() = _ingListLD

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _portionPfcLD = MutableLiveData<Pfc>()
    val portionPfcLD: LiveData<Pfc>
        get() = _portionPfcLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    init {
        updateList()
    }

    fun updateList() {
        viewModelScope.launch {
            _ingListLD.value = repo.getTable()
        }
    }

    fun calculate(portions: String) {
        viewModelScope.launch {
            try {
                val portionsInt = portions.toInt()
                _portionPfcLD.value = repo.calculatePortion(portionsInt)
            } catch (e: Exception) {
                _errorLD.value = Unit
            }
        }
    }

    fun delete(ingredient: Ingredient) {
        viewModelScope.launch {
            repo.delete(ingredient)
            updateList()
        }
    }

    fun saveProduct(title: String, type: Any) {
        val portionPfc = portionPfcLD.value
        if (title.trim().isEmpty() || portionPfc == null) {
            _errorLD.value = Unit
        } else {
            viewModelScope.launch {
                productRepo.add(
                    Product(
                        title = title,
                        proteins = portionPfc.protein,
                        fats = portionPfc.fat,
                        carbohydrates = portionPfc.carb,
                        type = type as ProductType,
                        archived = false
                    )
                )
                repo.deleteAll()
                _shouldFinishActivityLD.value = Unit
            }
        }
    }
}