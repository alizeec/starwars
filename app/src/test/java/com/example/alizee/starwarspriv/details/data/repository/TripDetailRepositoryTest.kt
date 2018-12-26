package com.example.alizee.starwarspriv.details.data.repository

import com.example.alizee.starwarspriv.core.data.network.TripNetwork
import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.details.data.network.TripDetailApi
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.Assert.*
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class TripDetailRepositoryTest {

    private val tripDetailApi by lazy { mock<TripDetailApi>() }
    private lateinit var repository: TripDetailRepository

    @Before
    fun setup(){
        repository = TripDetailRepository(tripDetailApi)
    }

    val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Test
    fun should_return_api(){
        //Given
        val id = 2
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
        doReturn(Observable.just(tripNetwork)).`when`(tripDetailApi).fetchTrip(id)

        //When
        val result = repository.fetchTrip(id)

        //Then
        val testObserver = TestObserver<Trip>()
        result.subscribe(testObserver)
        testObserver.assertComplete()
        testObserver.assertNoErrors()
        val tripResult = testObserver.values()[0]
        assertEquals(1, tripResult.id)
        assertEquals("Dark Vador", tripResult.pilot.name)
        assertEquals("Yavin 4", tripResult.pickup.name)
        assertEquals("Naboo", tripResult.dropoff.name)
    }
}