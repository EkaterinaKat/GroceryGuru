package com.katysh.groceryguru.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.katysh.groceryguru.databinding.ActivityDividerBinding

class DividerActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityDividerBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.weightEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculate()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.portionsEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculate()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun calculate() {

        try {
            val weight = binding.weightEt.text.toString().toInt()
            val portions = binding.portionsEt.text.toString().toInt()

            val portionSize = weight / portions

            var res = "Размер порции: $portionSize"
            for (i in 0..portions) {
                res += "\n${weight - portionSize * i}"
            }
            binding.resultView.text = res
        } catch (e: Exception) {
            binding.resultView.text = "-"
        }
    }
}