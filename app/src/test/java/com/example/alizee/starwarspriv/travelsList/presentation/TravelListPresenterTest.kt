package com.example.alizee.starwarspriv.travelsList.presentation

import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.example.alizee.starwarspriv.travelsList.data.repository.TravelListRepository
import com.example.alizee.starwarspriv.travelsList.domain.TripListing
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.trampoline
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test

class TravelListPresenterTest {
    private val repository by lazy { mock<TravelListRepository>() }
    private val screen by lazy { mock<TravelListScreen>() }

    private lateinit var presenter: TravelListPresenter
    private val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Before
    fun setup() {
        presenter = TravelListPresenter(trampoline(), trampoline(), repository)
        presenter.bind(screen)
    }

    @After
    fun tearDown() {
        presenter.unbind()
    }

    @Test
    fun fetchTrips_success() {
        //Given
        val trip1 = gson.fromJson<Trip>(
            """
            {
            "id": 1,
            "pilot": {
              "name": "Dark Vador",
              "avatar": "/static/admiral-ackbar.png",
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
        val trip2 = gson.fromJson<Trip>(
            """
             {
            "id": 2,
            "pilot": {
              "name": "Admiral Ackbar",
              "avatar": "/static/admiral-ackbar.png",
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
        doReturn(Observable.just(listOf(trip1, trip2))).`when`(repository).fetchAllTrips()
        val captureList = argumentCaptor<List<TripListing>>()

        //When
        presenter.start()

        //Then
        verify(screen).stopLoading()
        verify(screen).displayTrips(captureList.capture())
        val list = captureList.firstValue
        val tripList1 = list[0]
        assertEquals(1, tripList1.id)
        assertEquals("Dark Vador", tripList1.pilotName)
        val tripList2 = list[1]
        assertEquals(2, tripList2.id)
        assertEquals("Admiral Ackbar", tripList2.pilotName)
    }

    @Test
    fun fetchTrips_fail() {
        //Given
        doReturn(Observable.error<Throwable>(Throwable("error"))).`when`(repository).fetchAllTrips()

        //When
        presenter.start()

        //Then
        verify(screen).stopLoading()
        verify(screen).displayError(R.string.error_list)
    }

}