package com.example.alizee.starwarspriv.details.data.repository

import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.core.domain.toTripDomain
import com.example.alizee.starwarspriv.details.data.network.TripDetailApi
import io.reactivex.Observable
import javax.inject.Inject

internal class TripDetailRepository @Inject constructor(private val tripDetailApi: TripDetailApi) {

    fun fetchTrip(id: Int): Observable<Trip> {
        return tripDetailApi.fetchTrip(id).map { tripNetwork -> tripNetwork.toTripDomain() }
    }
}