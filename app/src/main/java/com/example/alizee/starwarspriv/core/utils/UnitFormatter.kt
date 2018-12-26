package com.example.alizee.starwarspriv.core.utils

interface UnitFormatter {
    fun formatDistanceToString(distance: Long, unit: String): String

    fun formatDurationToString(duration: Long): String
}