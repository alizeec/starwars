package com.example.alizee.starwarspriv.details.domain

import com.example.alizee.starwarspriv.core.BASE_URL
import com.example.alizee.starwarspriv.core.domain.Trip
import com.example.alizee.starwarspriv.core.utils.DateHelper
import com.example.alizee.starwarspriv.core.utils.UnitFormatter

class TripDetailTransformer constructor(private val dateHelper: DateHelper, private val unitFormatter: UnitFormatter) {
    fun tripToTripDetail(trip: Trip): TripDetail {

        val pickupTime = dateHelper.formatApiDateToTime(trip.pickup.date)
        val dropoffTime = dateHelper.formatApiDateToTime(trip.dropoff.date)
        val duration = unitFormatter.formatDurationToString(trip.duration)
        val distance = unitFormatter.formatDistanceToString(trip.distance.value, trip.distance.unit)

        var isRated = false
        var star1Filled = false
        var star2Filled = false
        var star3Filled = false
        var star4Filled = false
        var star5Filled = false

        val rating = trip.pilot.rating
        if (rating != null && rating > 0) {
            isRated = true
            if (rating >= 1f) star1Filled = true
            if (rating >= 2f) star2Filled = true
            if (rating >= 3f) star3Filled = true
            if (rating >= 4f) star4Filled = true
            if (rating == 5f) star5Filled = true
        }

        return TripDetail(
            trip.id,
            trip.pilot.name,
            BASE_URL+trip.pilot.avatar,
            isRated,
            star1Filled,
            star2Filled,
            star3Filled,
            star4Filled,
            star5Filled,
            trip.pickup.name,
            BASE_URL+trip.pickup.picture,
            pickupTime,
            trip.dropoff.name,
            BASE_URL+trip.dropoff.picture,
            dropoffTime,
            distance,
            duration
        )
    }
}