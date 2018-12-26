package com.example.alizee.starwarspriv.details.presentation

import com.example.alizee.starwarspriv.R
import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.details.data.repository.TripDetailRepository
import com.example.alizee.starwarspriv.details.domain.TripDetail
import com.example.alizee.starwarspriv.details.domain.TripDetailTransformer
import com.example.alizee.starwarspriv.testhelper.GsonBuilder
import com.google.gson.Gson
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers.trampoline
import org.junit.After
import org.junit.Before
import org.junit.Test

class TripDetailPresenterTest {
    private val repository by lazy { mock<TripDetailRepository>() }
    private val tripDetailTransformer by lazy { mock<TripDetailTransformer>() }
    private val screen by lazy { mock<TripDetailScreen>() }

    private lateinit var presenter: TripDetailPresenter
    private val gson: Gson = GsonBuilder.getDefaultBuilder().create()

    @Before
    fun setup() {
        presenter = TripDetailPresenter(trampoline(), trampoline(), repository, tripDetailTransformer)
        presenter.bind(screen)
    }

    @After
    fun tearDown() {
        presenter.unbind()
    }

    @Test
    fun fetch_trip_success_withRatings() {
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
        val tripDetail = gson.fromJson<TripDetail>(
            """
             {
             "id": 1,
             "pilot_name":"Dark Vador",
             "pilot_avatar":"/static/dark-vador.png",
             "is_rated":true,
             "star1_filled":true,
             "star2_filled":true,
             "star3_filled":true,
             "star4_filled":true,
             "star5_filled":false,
             "pickup_name":"Yavin 4",
             "pickup_image":"/static/yavin-4.png",
             "pickup_time":"14:12",
             "dropoff_name":"Naboo",
             "dropoff_image":"/static/naboo.png",
             "dropoff_time":"19:35",
             "distance":"2,478,572 km",
             "duration":"19:42:70:00"
             }
         """.trimIndent()
            , TripDetail::class.java
        )
        doReturn(Observable.just(trip)).`when`(repository).fetchTrip(1)
        doReturn(tripDetail).`when`(tripDetailTransformer).tripToTripDetail(trip)

        // When
        presenter.start(1)

        //Then
        verify(screen).stopLoading()

        verify(screen).displayForegroundPlanet("/static/naboo.png")
        verify(screen).displayBackgroundPlanet("/static/yavin-4.png")
        verify(screen).displayPilotAvatar("/static/dark-vador.png")
        verify(screen).displayPilotName("Dark Vador")
        verify(screen).displayPickupName("Yavin 4")
        verify(screen).displayPickupTime("14:12")
        verify(screen).displayDropoffName("Naboo")
        verify(screen).displayDropoffTime("19:35")
        verify(screen).displayDistance("2,478,572 km")
        verify(screen).displayDuration("19:42:70:00")
        verify(screen).displayRatings(true, true, true, true, false)
    }

    @Test
    fun fetch_trip_success_withoutRatings() {
        //Given
        val trip = gson.fromJson<Trip>(
            """
             {
    "id": 1,
    "pilot": {
      "name": "Dark Vador",
      "avatar": "/static/dark-vador.png"
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
        val tripDetail = gson.fromJson<TripDetail>(
            """
             {
             "id": 1,
             "pilot_name":"Dark Vador",
             "pilot_avatar":"/static/dark-vador.png",
             "is_rated":false,
             "star1_filled":false,
             "star2_filled":false,
             "star3_filled":false,
             "star4_filled":false,
             "star5_filled":false,
             "pickup_name":"Yavin 4",
             "pickup_image":"/static/yavin-4.png",
             "pickup_time":"14:12",
             "dropoff_name":"Naboo",
             "dropoff_image":"/static/naboo.png",
             "dropoff_time":"19:35",
             "distance":"2,478,572 km",
             "duration":"19:42:70:00"
             }
         """.trimIndent()
            , TripDetail::class.java
        )
        doReturn(Observable.just(trip)).`when`(repository).fetchTrip(1)
        doReturn(tripDetail).`when`(tripDetailTransformer).tripToTripDetail(trip)

        // When
        presenter.start(1)

        //Then
        verify(screen).stopLoading()

        verify(screen).displayForegroundPlanet("/static/naboo.png")
        verify(screen).displayBackgroundPlanet("/static/yavin-4.png")
        verify(screen).displayPilotAvatar("/static/dark-vador.png")
        verify(screen).displayPilotName("Dark Vador")
        verify(screen).displayPickupName("Yavin 4")
        verify(screen).displayPickupTime("14:12")
        verify(screen).displayDropoffName("Naboo")
        verify(screen).displayDropoffTime("19:35")
        verify(screen).displayDistance("2,478,572 km")
        verify(screen).displayDuration("19:42:70:00")
        verify(screen).hideRatings()
    }

    @Test
    fun fetch_trip_fail() {
        //Given
        doReturn(Observable.error<Throwable>(Throwable("error"))).`when`(repository).fetchTrip(1)

        // When
        presenter.start(1)

        //Then
        verify(screen).stopLoading()
        verify(screen).displayError(R.string.error_detail)
    }


}