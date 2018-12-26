package com.example.alizee.starwarspriv.details.domain

import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.core.utils.DateHelper
import com.example.alizee.starwarspriv.core.utils.UnitFormatter
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.text.SimpleDateFormat

class TripDetailTransformerTest {
    private val dateHelper by lazy { mock<DateHelper>() }
    private val unitFormatter by lazy { mock<UnitFormatter>() }

    private lateinit var transformer: TripDetailTransformer

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Before
    fun setup() {
        transformer = TripDetailTransformer(dateHelper, unitFormatter)
    }

    @Test
    fun trip_shouldBeConvertedTo_tripDetail() {
        //Given
        val trip = gson.fromJson<Trip>(
            """
             {
    "id": 1,
    "pilot": {
      "name": "Dark Vador",
      "avatar": "/static/dark-vador.png",
      "rating": 4.0
    },
    "distance": {
      "value": 2478572,
      "unit": "km"
    },
    "duration": 19427000,
    "pickup": {
      "name": "Yavin 4",
      "picture": "/static/yavin-4.png",
      "date": "2017-12-09T14:12:51Z"
    },
    "dropoff": {
      "name": "Naboo",
      "picture": "/static/naboo.png",
      "date": "2017-12-09T19:35:51Z"
    }
  }
         """.trimIndent()
            , Trip::class.java
        )
        doReturn("14:12").`when`(dateHelper)
            .formatApiDateToTime(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2017-12-09T14:12:51Z"))
        doReturn("19:35").`when`(dateHelper)
            .formatApiDateToTime(SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse("2017-12-09T19:35:51Z"))
        doReturn("19:42:70:00").`when`(unitFormatter).formatDurationToString(19427000)
        doReturn("2,478,572 km").`when`(unitFormatter).formatDistanceToString(2478572, "km")

        //When
        val tripDetail = transformer.tripToTripDetail(trip)

        //Then
        assertEquals(1, tripDetail.id)
        assertEquals("Dark Vador", tripDetail.pilotName)
        assertEquals("https://starwars.chauffeur-prive.com/static/dark-vador.png", tripDetail.pilotAvatar)
        assertEquals(true, tripDetail.isRated)
        assertEquals(true, tripDetail.star1Filled)
        assertEquals(true, tripDetail.star2Filled)
        assertEquals(true, tripDetail.star3Filled)
        assertEquals(true, tripDetail.star4Filled)
        assertEquals(false, tripDetail.star5Filled)
        assertEquals("Yavin 4", tripDetail.pickupName)
        assertEquals("https://starwars.chauffeur-prive.com/static/yavin-4.png", tripDetail.pickupImage)
        assertEquals("14:12", tripDetail.pickupTime)
        assertEquals("Naboo", tripDetail.dropoffName)
        assertEquals("https://starwars.chauffeur-prive.com/static/naboo.png", tripDetail.dropoffImage)
        assertEquals("19:35", tripDetail.dropoffTime)
        assertEquals("2,478,572 km", tripDetail.distance)
        assertEquals("19:42:70:00", tripDetail.duration)
    }

}