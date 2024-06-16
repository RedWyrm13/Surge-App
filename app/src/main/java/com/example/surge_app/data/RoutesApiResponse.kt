package com.example.surge_app.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
//Routes kotlin object to hold the information obtained from the Routes API.

@Serializable
data class RouteResponse(
    val routes: List<Route>
)

@Serializable
data class Route(
    val distanceMeters: Int,
    val duration: String,
    val polyline: Polyline,
    val warnings: List<String>? = null,
    val description: String,

)

@Serializable
data class Polyline(
    val encodedPolyline: String
)

fun convertFromJsonStringToRoutesResponse(jsonString: String): RouteResponse {
    return Json.decodeFromString<RouteResponse>(jsonString)
}
//This function is used to convert the time it takes to drive the returned route from seconds into hours and minutes
//For example 3660 seconds would be converted into 1 hour and 1 minute.
fun secondsToHoursMinutes(seconds: Int): String {
    val hours = seconds / 3600
    val remainingSeconds = seconds % 3600
    val minutes = remainingSeconds / 60
    if (hours == 0) return "$minutes minutes"
    return "$hours hours $minutes minutes"
}
fun metersToMiles(meters: Int): Double {
    val milesConversionFactor = 0.000621371
    val miles = meters * milesConversionFactor
    return String.format("%.1f", miles).toDouble()
}
