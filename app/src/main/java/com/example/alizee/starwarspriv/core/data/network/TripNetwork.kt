package com.example.alizee.starwarspriv.core.data.network

import com.google.gson.annotations.SerializedName
import java.util.*

data class TripNetwork(val id: Int, val pilot: PilotNetwork, val distance: DistanceNetwork, val duration: Long,
                       @SerializedName("pick_up") val pickup: WaypointNetwork,
                       @SerializedName("drop_off") val dropoff: WaypointNetwork
)

data class PilotNetwork(val name: String, val avatar: String, val rating: Float?)

data class DistanceNetwork(val value: Long, val unit: String)

data class WaypointNetwork(val name: String, val picture: String, val date: Date)