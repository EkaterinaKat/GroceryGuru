package com.katysh.groceryguru.util

import android.app.DatePickerDialog
import android.content.Context
import android.util.Log
import android.widget.TextView
import java.util.Date

class DatePicker(
    private val context: Context,
    private val tv: TextView,
    private val initDate: Date? = null
) {

    var selectedDate: Date? = null

    init {
        tv.text = getDateString(initDate)
        Log.i("tag6483", "tv.setOnClickListener { openDatePicker() }")
        tv.setOnClickListener { openDatePicker() }
    }

    private fun openDatePicker() {
        Log.i("tag6483", "openDatePicker()")
        val datePickerDialog = DatePickerDialog(context)
        datePickerDialog.setOnDateSetListener { _, year, month, day ->
            selectedDate = parse(year, month + 1, day)
            setTvText()
        }
        datePickerDialog.show()
    }

    private fun setTvText() {
        tv.text = getDateString(selectedDate)
    }
}