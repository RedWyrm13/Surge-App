package com.example.surge_app.data


import com.example.surge_app.data.apiResponseData.Location
import java.util.Random

//This class will hold all the data for a ride and will contain methods to upload the ride to firebase
class Ride(
    val rideId: String = generateUniqueId("ride"),
    val pickupLocation: Location = Location(0.0, 0.0)   ,  //Harry reid international airport
    val destination: Location =  Location(36.1694, -115.1387), //El cortez hotel and casino
    val duration: String = "",
    val distance: Int = 0,
    val encodedPolyline: String? = null,
    val pickupLocationAddress: String = "", // Using the coordinates, the address will be obtained
    val destinationLocationAddress: String = "" // We can directly pass the address since the user confirms the address before starting the ride

) {
    //This data will be updated once a driver has been determined
    var paxIdNumber = ""
    var driverIdNumber = ""

}

//Used to create a unique id for rides, pax, and drivers
fun generateUniqueId(method: String): String {
    val chars = "ABCEFGHIJKLMNOQSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = Random()
    val sb = StringBuilder(15)
    for (i in 0 until 15) {
        sb.append(chars[random.nextInt(chars.length)])
    }
    when (method.lowercase().trim()) {
        "ride" -> {
            return "R" + sb.toString()
        }
        "pax" -> {
            return "P" + sb.toString()
        }
        "driver" -> {
            return "D" + sb.toString()
        }
    }
    return sb.toString()
}


