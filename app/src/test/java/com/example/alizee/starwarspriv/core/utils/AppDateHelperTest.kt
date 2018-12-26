package com.example.alizee.starwarspriv.core.utils

import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class AppDateHelperTest {
    @Test
    fun shouldConvert_date_to_time(){
        //Given
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse("2018-12-22T23:10:00")
        Locale.setDefault(Locale("fr", "FR"))

        //When
        val result = AppDateHelper().formatApiDateToTime(date)

        //Then
        assertEquals("23:10", result)
    }
}