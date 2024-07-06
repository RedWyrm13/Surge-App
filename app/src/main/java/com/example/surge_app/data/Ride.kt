package com.example.surge_app.data


import com.firebase.geofire.GeoFireUtils
import com.firebase.geofire.GeoLocation

//This class will hold all the data for a ride and will contain methods to upload the ride to firebase
class Ride(
    val rideId: String = generateUniqueId("ride"),
    val pickupLocation: SimpleLocation = SimpleLocation(latitude = 0.0, longitude = 0.0),
    val destinationLocation: SimpleLocation = SimpleLocation(latitude = 0.0, longitude = 0.0),
    val duration: Int = 0,
    val distance: Int = 0,
    val encodedPolyline: String? = null,
    val pickupLocationAddress: String = "", // Using the coordinates, the address will be obtained
    val destinationLocationAddress: String = "" // We can directly pass the address since the user confirms the address before starting the ride

) {
    //This data will be updated once a driver has been determined
    var paxIdNumber = ""
    var driverIdNumber = ""
    val geohash = GeoFireUtils.getGeoHashForLocation(GeoLocation(pickupLocation.latitude, pickupLocation.longitude))

    override fun toString(): String {
        return "Ride(rideId='$rideId', pickupLocation=$pickupLocation, destination=$destinationLocation, duration='$duration', distance=$distance, encodedPolyline=$encodedPolyline, pickupLocationAddress='$pickupLocationAddress', destinationLocationAddress='$destinationLocationAddress', paxIdNumber='$paxIdNumber', driverIdNumber='$driverIdNumber', geohash='$geohash')"
    }

}

//Used to create a unique id for rides, pax, and drivers

data class SimpleLocation(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
){
    override fun toString(): String {
        return this.latitude.toString() + ", " + this.longitude.toString()
    }


}
fun stringToInt(input: String): Int {
    // Check if the input ends with 's'
    if (input.endsWith("s")) {
        // Remove the 's' at the end and parse the remaining string to an integer
        return input.dropLast(1).toIntOrNull() ?: throw IllegalArgumentException("Invalid input format")
    } else {
        throw IllegalArgumentException("Input should end with 's'")
    }
}

fun Int.minutesHours(): String{
    val totalMinutes = this / 60
    val hours = totalMinutes / 60
    val minutes = totalMinutes % 60
    return if (hours > 0) {
        "$hours hours, $minutes minutes"
    } else {
        "$minutes minutes"
    }
}