package com.katysh.groceryguru.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date


private val READABLE_DATE_FORMAT: DateFormat = SimpleDateFormat("dd.MM.yyyy")

fun getDateString(date: Date?): String {
    if (date == null)
        return "*"
    return READABLE_DATE_FORMAT.format(date)
}

fun getDateString(year: Int, month: Int, day: Int): String {
    return String.format("%d.%s.%d", day, if (month < 10) "0$month" else month, year)
}

fun parse(year: Int, month: Int, day: Int): Date? {
    return try {
        READABLE_DATE_FORMAT.parse(
            String.format(
                "%d.%s.%d",
                day,
                if (month < 10) "0$month" else month,
                year
            )
        )
    } catch (e: ParseException) {
        throw RuntimeException()
    }
}