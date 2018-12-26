package com.example.alizee.starwarspriv.travelsList.data.network

import com.example.alizee.starwarspriv.core.data.network.TripNetwork
import io.reactivex.Observable
import retrofit2.http.GET

interface TravelListApi {

    @GET("/trips")
    fun fetchTrips(): Observable<List<TripNetwork>>
}