package com.katysh.groceryguru.util

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
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

fun parse(year: Int, month: Int, day: Int): Date {
    try {
        return READABLE_DATE_FORMAT.parse(
            String.format(
                "%d.%s.%d",
                day,
                if (month < 10) "0$month" else month,
                year
            )
        ) ?: throw RuntimeException()
    } catch (e: ParseException) {
        throw RuntimeException()
    }
}

fun daysLeft(expirationDate: Date): Int {
    return getNumberOfDays(removeTimeFromDate(Date()), removeTimeFromDate(expirationDate)) + 1
}

/**
 * @return Число дней между датами. Если даты соседние, то число дней = 1.
 * Если даты совпадают, то число дней = 0.
 * Если startDate раньше finishDate, то число положительное и наоборот.
 */
fun getNumberOfDays(startDate: Date, finishDate: Date): Int {
    val diff = finishDate.time - startDate.time
    return (diff / 86400000).toInt()
}

fun removeTimeFromDate(date: Date): Date {
    return try {
        READABLE_DATE_FORMAT.parse(READABLE_DATE_FORMAT.format(date))
    } catch (e: ParseException) {
        throw java.lang.RuntimeException()
    }
}

fun equalsIgnoreTime(date1: Date?, date2: Date?): Boolean {
    val calendar1 = Calendar.getInstance()
    calendar1.time = date1
    val calendar2 = Calendar.getInstance()
    calendar2.time = date2
    return calendar1[Calendar.DATE] == calendar2[Calendar.DATE]
            && calendar1[Calendar.MONTH] == calendar2[Calendar.MONTH]
            && calendar1[Calendar.YEAR] == calendar2[Calendar.YEAR]
}

fun shiftDate(date: Date?, unit: TimeUnit, numOfUnits: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.add(unit.intRepresentationForCalendar, numOfUnits)
    return calendar.time
}


enum class TimeUnit(val intRepresentationForCalendar: Int) {
    DAY(Calendar.DATE),
    MONTH(Calendar.MONTH),
    YEAR(Calendar.YEAR)

}

fun getDateStringWithWeekDay(date: Date?): String {
    if (date == null) {
        return "нуль"
    }
    val calendar = Calendar.getInstance()
    calendar.time = date
    when (calendar[Calendar.DAY_OF_WEEK]) {
        1 -> return getDateString(date) + " (Вс)"
        2 -> return getDateString(date) + " (Пн)"
        3 -> return getDateString(date) + " (Вт)"
        4 -> return getDateString(date) + " (Ср)"
        5 -> return getDateString(date) + " (Чт)"
        6 -> return getDateString(date) + " (Пт)"
        7 -> return getDateString(date) + " (Сб)"
    }
    throw java.lang.RuntimeException()
}
