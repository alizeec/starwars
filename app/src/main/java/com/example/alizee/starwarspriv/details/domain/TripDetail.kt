package com.example.alizee.starwarspriv.details.domain


data class TripDetail(
    val id: Int,
    val pilotName: String,
    val pilotAvatar: String,
    val isRated: Boolean,
    val star1Filled: Boolean,
    val star2Filled: Boolean,
    val star3Filled: Boolean,
    val star4Filled: Boolean,
    val star5Filled: Boolean,
    val pickupName: String,
    val pickupImage: String,
    val pickupTime: String,
    val dropoffName: String,
    val dropoffImage: String,
    val dropoffTime: String,
    val distance: String,
    val duration: String
)