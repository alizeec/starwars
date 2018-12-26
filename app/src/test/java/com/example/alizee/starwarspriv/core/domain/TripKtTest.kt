package com.example.alizee.starwarspriv.core.domain

import com.example.alizee.starwarspriv.core.data.network.TripNetwork
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class TripKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()


    @Test
    fun tripNetwork_shouldBeConvertedTo_Trip() {
        //Given
        val tripNetwork = gson.fromJson<TripNetwork>(
            """
             {
    "id": 1,
    "pilot": {
      "name": "Dark Vador",
      "avatar": "/static/dark-vador.png",
      "rating": 5.0
    },
    "distance": {
      "value": 2478572,
      "unit": "km"
    },
    "duration": 19427000,
    "pick_up": {
      "name": "Yavin 4",
      "picture": "/static/yavin-4.png",
      "date": "2017-12-09T14:12:51Z"
    },
    "drop_off": {
      "name": "Naboo",
      "picture": "/static/naboo.png",
      "date": "2017-12-09T19:35:51Z"
    }
  }
         """.trimIndent()
            , TripNetwork::class.java
        )

        //When
        val trip = tripNetwork.toTripDomain()

        //Then
        assertEquals(1, trip.id)
        assertEquals("Dark Vador", trip.pilot.name)
        assertEquals("/static/dark-vador.png", trip.pilot.avatar)
        assertEquals(5.0f, trip.pilot.rating)
        assertEquals(2478572, trip.distance.value)
        assertEquals("km", trip.distance.unit)
        assertEquals(19427000, trip.duration)
        assertEquals("Yavin 4", trip.pickup.name)
        assertEquals("/static/yavin-4.png", trip.pickup.picture)
        assertEquals("Naboo", trip.dropoff.name)
        assertEquals("/static/naboo.png", trip.dropoff.picture)
    }
}