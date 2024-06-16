package com.example.surge_app.data


//This class will hold all the data for a ride and will contain methods to upload the ride to firebase
class Ride(
    val pax: Pax,
    val pickupLocation: Location,
    val dropoffLocation: Location,
    val duration: String,
    val distance: Double,
) {
    //This data will be updated once a driver has been determined
     var driver = Driver()



}