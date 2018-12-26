package com.example.alizee.starwarspriv.travelsList.domain

import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.google.gson.Gson
import org.junit.Assert.*
import org.junit.Test

class TripListingKtTest {

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun trip_shouldBeConvertedTo_tripListing(){
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

        //When
        val tripListing = trip.toPresentationList()

        //Then
        assertEquals(1, tripListing.id)
        assertEquals("Dark Vador", tripListing.pilotName)
        assertEquals("https://starwars.chauffeur-prive.com/static/dark-vador.png", tripListing.pilotAvatar)
        assertEquals(true, tripListing.isRated)
        assertEquals(true, tripListing.star1Filled)
        assertEquals(true, tripListing.star2Filled)
        assertEquals(true, tripListing.star3Filled)
        assertEquals(true, tripListing.star4Filled)
        assertEquals(false, tripListing.star5Filled)
        assertEquals("Yavin 4", tripListing.pickup)
        assertEquals("Naboo", tripListing.dropoff)
    }
}