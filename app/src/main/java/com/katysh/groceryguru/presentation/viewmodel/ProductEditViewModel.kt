package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.db.ProductRepo
import com.katysh.groceryguru.model.Product
import com.katysh.groceryguru.model.ProductType
import kotlinx.coroutines.launch

class ProductEditViewModel(
    private val productRepo: ProductRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    fun validateAndSave(
        product: Product?, title: String?, desc: String?,
        pr: String?, fat: String?, carb: String?, type: Any
    ) {
        if (title == null || title.trim().isEmpty()
            || pr == null || pr.trim().isEmpty()
            || fat == null || fat.trim().isEmpty()
            || carb == null || carb.trim().isEmpty()
        ) {
            _errorLD.value = Unit
        } else {
            save(product, title, desc, pr.toInt(), fat.toInt(), carb.toInt(), type as ProductType)
        }
    }

    private fun save(
        product: Product?,
        title: String,
        desc: String?,
        pr: Int,
        fat: Int,
        carb: Int,
        type: ProductType
    ) {
        viewModelScope.launch {
            if (product == null) {
                val newProduct =
                    Product(
                        title = title,
                        desc = desc,
                        proteins = pr,
                        fats = fat,
                        carbohydrates = carb,
                        type = type
                    )
                productRepo.add(newProduct)
            } else {
                product.title = title
                product.desc = desc
                product.proteins = pr
                product.fats = fat
                product.carbohydrates = carb
                product.type = type
                productRepo.edit(product)
            }


            _shouldFinishActivityLD.value = Unit
        }
    }

}