package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.EntryRepo
import com.katysh.groceryguru.model.Entry
import com.katysh.groceryguru.model.EntryWithProduct
import com.katysh.groceryguru.model.MealNum
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Date

class EntryEditViewModel(
    private val entryRepo: EntryRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    private val _defaultMealNumLD = MutableLiveData<MealNum>()
    val defaultMealNumLD: LiveData<MealNum>
        get() = _defaultMealNumLD

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _defaultMealNumLD.postValue(entryRepo.getDefaultMealNum())
        }
    }

    fun validateAndSave(
        existing: EntryWithProduct?,
        product: Product?,
        weight: String?,
        date: Date?,
        mealNum: MealNum?
    ) {
        if (product == null
            || weight == null || weight.trim().isEmpty()
            || date == null || mealNum == null
        ) {
            _errorLD.value = Unit
        } else {

            save(existing?.entry, product, weight.toInt(), date, mealNum)
        }
    }

    private fun save(
        existing: Entry?,
        product: Product,
        weight: Int,
        date: Date,
        mealNum: MealNum
    ) {
        viewModelScope.launch {
            if (existing == null) {
                val entry = Entry(
                    productId = product.id,
                    weight = weight,
                    date = date,
                    mealNum = mealNum
                )
                entryRepo.add(entry)
            } else {
                existing.productId = product.id
                existing.weight = weight
                existing.date = date
                existing.mealNum = mealNum
                entryRepo.update(existing)
            }

            _shouldFinishActivityLD.value = Unit
        }
    }
}