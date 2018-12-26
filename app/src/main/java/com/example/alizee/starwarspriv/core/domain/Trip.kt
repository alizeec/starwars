package com.example.alizee.starwarspriv.core.domain

import com.example.alizee.starwarspriv.core.data.network.TripNetwork
import java.util.*

data class Trip(
    val id: Int, val pilot: Pilot, val distance: Distance, val duration: Long,
    val pickup: Waypoint,
    val dropoff: Waypoint
)

data class Pilot(val name: String, val avatar: String, val rating: Float?)

data class Distance(val value: Long, val unit: String)

data class Waypoint(val name: String, val picture: String, val date: Date)

fun TripNetwork.toTripDomain(): Trip {
    val pickup = Waypoint(pickup.name, pickup.picture, pickup.date)
    val dropoff = Waypoint(dropoff.name, dropoff.picture, dropoff.date)
    val pilot = Pilot(pilot.name, pilot.avatar, pilot.rating)
    val distance = Distance(distance.value, distance.unit)

    return Trip(id, pilot, distance, duration, pickup, dropoff)
}