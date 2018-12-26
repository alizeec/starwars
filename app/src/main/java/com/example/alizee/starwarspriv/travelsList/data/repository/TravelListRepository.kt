package com.example.alizee.starwarspriv.travelsList.data.repository

import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.core.domain.toTripDomain
import com.example.alizee.starwarspriv.travelsList.data.network.TravelListApi
import io.reactivex.Observable
import javax.inject.Inject

internal class TravelListRepository @Inject constructor(private val travelListApi: TravelListApi) {

    fun fetchAllTrips(): Observable<List<Trip>> {
        return travelListApi.fetchTrips().map { tripNetworkList -> tripNetworkList.map { it.toTripDomain() } }
    }
}