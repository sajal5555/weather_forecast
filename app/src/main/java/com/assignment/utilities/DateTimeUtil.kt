package com.assignment.utilities

import java.text.SimpleDateFormat
import java.util.*

class DateTimeUtil {

    fun getDate(milliTime: Int): String {
        val currentDate = Date(milliTime.toLong() * 1000)
        val df = SimpleDateFormat("E, dd.MM.yyyy - hh:mm a", Locale.getDefault())
        return df.format(currentDate)
    }

    fun getDay(milliTime: Int): String {
        val currentDate = Date(milliTime.toLong() * 1000)
        val df = SimpleDateFormat("dd", Locale.getDefault())
        return df.format(currentDate)
    }
}