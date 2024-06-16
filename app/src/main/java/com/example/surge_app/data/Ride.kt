package com.example.surge_app.data


import java.util.Random

//This class will hold all the data for a ride and will contain methods to upload the ride to firebase
class Ride(
    val rideId: String = generateRideId(),
    val pickupLocation: Location =  Location(36.0831, -115.1473),     //Harry reid international airport
    val destination: Location =  Location(36.1694, -115.1387),     //El cortez hotel and casino
    val duration: String = "",
    val distance: Int = 0,
    val encodedPolyline: String? = null,
) {
    //This data will be updated once a driver has been determined
    var paxIdNumber = ""
    var driverIdNumber = ""
    val pickupLocationAddress: String = "" // Using the coordinates, the address will be obtained
    val destinationLocationAddress: String = "" // We can directly pass the address since the user confirms the address before starting the ride







}



fun generateRideId(): String {
    val chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = Random()
    val sb = StringBuilder(12)
    for (i in 0 until 12) {
        sb.append(chars[random.nextInt(chars.length)])
    }
    return sb.toString()
}


