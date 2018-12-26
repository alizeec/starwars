package com.example.alizee.starwarspriv.details.presentation

internal interface TripDetailScreen {

    fun displayForegroundPlanet(urlImage: String)

    fun displayBackgroundPlanet(urlImage: String)

    fun displayPilotAvatar(urlAvatar: String)

    fun displayPilotName(name: String)

    fun displayPickupName(name: String)

    fun displayPickupTime(time: String)

    fun displayDropoffName(name: String)

    fun displayDropoffTime(time: String)

    fun displayDistance(distance: String)

    fun displayDuration(duration: String)

    fun hideRatings()

    fun displayRatings(
        star1Filled: Boolean,
        star2Filled: Boolean,
        star3Filled: Boolean,
        star4Filled: Boolean,
        star5Filled: Boolean
    )

    fun stopLoading()

    fun displayError(error: Int)
}