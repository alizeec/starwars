package com.example.alizee.starwarspriv.core.utils

import org.junit.Assert.*
import org.junit.Test

class AppUnitFormatterTest {

    @Test
    fun should_format_duration(){
        //Given
        val duration = 19427000L

        //When
        val result = AppUnitFormatter().formatDurationToString(duration)

        //Then
        assertEquals("19:42:70:00", result)
    }

    @Test
    fun should_format_distance(){
        //Given
        val distance = 2478572L
        val unit = "km"

        //When
        val result = AppUnitFormatter().formatDistanceToString(distance, unit)

        //Then
        assertEquals("2,478,572 km", result)
    }
}