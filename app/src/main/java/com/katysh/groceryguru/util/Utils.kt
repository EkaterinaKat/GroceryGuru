package com.katysh.groceryguru.util

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

const val UNSPECIFIED_ID = 0

fun isNotBlank(string: String?): Boolean {
    return string != null && string.trim() != ""
}

fun <T> adjustSpinner(
    context: Context,
    spinner: Spinner,
    items: List<T>,
    spinnerListener: OneInKnob<T>?
) {
    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    spinner.adapter = adapter
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, position: Int, id: Long) {
            spinnerListener?.execute(items[position])
        }

        override fun onNothingSelected(arg0: AdapterView<*>?) {}
    }
}

fun <T> selectSpinnerItemByValue(spinner: Spinner, value: T) {
    for (position in 0 until spinner.count) {
        if (spinner.getItemAtPosition(position) == value) {
            spinner.setSelection(position)
            return
        }
    }
}