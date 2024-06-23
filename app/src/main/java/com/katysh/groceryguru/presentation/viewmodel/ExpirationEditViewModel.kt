package com.katysh.groceryguru.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.katysh.groceryguru.domain.ExpirationRepo
import com.katysh.groceryguru.domain.ProductRepo
import com.katysh.groceryguru.model.ExpirationEntry
import com.katysh.groceryguru.model.Product
import kotlinx.coroutines.launch
import java.util.Date

class ExpirationEditViewModel(
    private val productRepo: ProductRepo,
    private val expirationRepo: ExpirationRepo
) : ViewModel() {

    private val _errorLD = MutableLiveData<Unit>()
    val errorLD: LiveData<Unit>
        get() = _errorLD

    private val _shouldFinishActivityLD = MutableLiveData<Unit>()
    val shouldFinishActivityLD: LiveData<Unit>
        get() = _shouldFinishActivityLD

    val productsLD: LiveData<List<Product>>
        get() = productRepo.getListLd()

    fun validateAndSave(product: Product?, comment: String?, start: Date?, end: Date?) {
        if (product == null || (start == null && end == null)) {
            _errorLD.value = Unit
        } else {
            save(product, comment, start, end)
        }
    }

    private fun save(product: Product, comment: String?, start: Date?, end: Date?) {
        viewModelScope.launch {
            val exp = ExpirationEntry(
                productId = product.id,
                comment = comment,
                startDate = start,
                expirationDate = end
            )
            expirationRepo.add(exp)
            _shouldFinishActivityLD.value = Unit
        }
    }
}