package com.example.alizee.starwarspriv.core.utils

import java.text.DateFormat
import java.util.*

class AppDateHelper : DateHelper {

    /**
     * Format Date to a string with the time
     *
     * ex : input Date()
     * output : 12:34
     */
    override fun formatApiDateToTime(date: Date): String {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(date)
    }
}