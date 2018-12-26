package com.example.alizee.starwarspriv.travelsList.presentation

import android.support.annotation.StringRes
import com.example.alizee.starwarspriv.travelsList.domain.TripListing

internal interface TravelListScreen {
    fun displayTrips(trips: List<TripListing>)

    fun stopLoading()

    fun displayError(@StringRes error: Int)
}