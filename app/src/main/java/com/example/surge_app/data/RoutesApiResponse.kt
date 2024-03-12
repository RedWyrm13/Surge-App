package com.example.surge_app.data

import kotlinx.serialization.Serializable

@Serializable
data class RouteResponse(
    val routes: List<Route>
)

@Serializable
data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: Polyline
)

@Serializable
data class Polyline(
    val encodedPolyline: String
)
