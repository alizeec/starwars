package com.example.alizee.starwarspriv.details.data.network

import com.example.alizee.starwarspriv.core.data.network.TripNetwork
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface TripDetailApi {

    @GET("/trips/{id}")
    fun fetchTrip(@Path("id") id: Int): Observable<TripNetwork>
}