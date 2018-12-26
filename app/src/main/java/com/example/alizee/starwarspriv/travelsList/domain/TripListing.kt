package com.example.alizee.starwarspriv.travelsList.domain

import com.example.alizee.starwarspriv.core.BASE_URL
import com.example.alizee.starwarspriv.core.domain.Trip

data class TripListing(
    val id: Int,
    val pilotName: String,
    val pilotAvatar: String,
    val isRated: Boolean,
    val star1Filled: Boolean,
    val star2Filled: Boolean,
    val star3Filled: Boolean,
    val star4Filled: Boolean,
    val star5Filled: Boolean,
    val pickup: String,
    val dropoff: String
)

fun Trip.toPresentationList(): TripListing {

    var isRated = false
    var star1Filled = false
    var star2Filled = false
    var star3Filled = false
    var star4Filled = false
    var star5Filled = false

    if (pilot.rating != null && pilot.rating > 0) {
        isRated = true
        if (pilot.rating >= 1f) star1Filled = true
        if (pilot.rating >= 2f) star2Filled = true
        if (pilot.rating >= 3f) star3Filled = true
        if (pilot.rating >= 4f) star4Filled = true
        if (pilot.rating == 5f) star5Filled = true
    }

    return TripListing(
        id,
        pilot.name,
        BASE_URL+pilot.avatar,
        isRated,
        star1Filled,
        star2Filled,
        star3Filled,
        star4Filled,
        star5Filled,
        pickup.name,
        dropoff.name
    )
}