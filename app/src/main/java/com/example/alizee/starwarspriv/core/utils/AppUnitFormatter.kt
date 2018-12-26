package com.example.alizee.starwarspriv.core.utils

import java.lang.StringBuilder

class AppUnitFormatter : UnitFormatter {

    override fun formatDurationToString(duration: Long) = formatWithDelimiter(duration.toString(), 2, ":")

    override fun formatDistanceToString(distance: Long, unit: String): String {
        val formattedDistance = formatWithDelimiter(distance.toString(), 3, ",")
        return "$formattedDistance $unit"
    }

    private fun formatWithDelimiter(value: String, interval: Int, delimiter: String): String {
        val reversedString = value.reversed()
        val chuncks = reversedString.chunked(interval)
        val resultBuilder = StringBuilder().append(chuncks[chuncks.size - 1].reversed())
        for (i in (chuncks.size - 2) downTo 0) {
            resultBuilder.append(delimiter).append(chuncks[i].reversed())
        }
        return resultBuilder.toString()
    }

}