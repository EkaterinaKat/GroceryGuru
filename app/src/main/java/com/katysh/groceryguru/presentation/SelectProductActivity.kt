package com.katysh.groceryguru.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.katysh.groceryguru.R
import com.katysh.groceryguru.databinding.ActivitySelectProductBinding
import com.katysh.groceryguru.model.ProductWithPortions
import com.katysh.groceryguru.presentation.EntryEditActivity.Companion.PRODUCT_RESULT_KEY


class SelectProductActivity : AppCompatActivity(), ProductListFragment.OnProductClickListener {

    private val binding by lazy {
        ActivitySelectProductBinding.inflate(layoutInflater)
    }

    private val listFragment = ProductListFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, listFragment)
            .commit()
    }

    override fun onProductClick(product: ProductWithPortions) {
        val resultIntent = Intent().apply {
            putExtra(PRODUCT_RESULT_KEY, product)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}