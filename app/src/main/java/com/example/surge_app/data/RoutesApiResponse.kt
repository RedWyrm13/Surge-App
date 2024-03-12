package com.example.surge_app.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

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

fun convertFromJsonStringToRoutesResponse(jsonString: String): RouteResponse {
    return Json.decodeFromString<RouteResponse>(jsonString)
}