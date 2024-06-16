package com.example.surge_app.network

import android.content.Context
import android.util.Log
import com.example.surge_app.data.Ride
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseManager {

    private var passengerAppInitialized = false
    private lateinit var passengerApp: FirebaseApp
    private lateinit var passengerDb: FirebaseFirestore

    private var driverAppInitialized = false
    private lateinit var driverApp: FirebaseApp
    private lateinit var driverDb: FirebaseFirestore

    private fun initializePassengerApp(context: Context) {
        if (!passengerAppInitialized) {
            passengerApp = FirebaseApp.initializeApp(context)!!
            passengerDb = FirebaseFirestore.getInstance(passengerApp)
            passengerAppInitialized = true
        }
    }

    private fun initializeDriverApp(context: Context) {
        if (!driverAppInitialized) {
            try {
                val options = FirebaseOptions.Builder()
                    .setApplicationId("1:201633923016:android:617388de46b571d4774afd")
                    .setApiKey("AIzaSyDsSGmGIHxU-BMU-R0XB3DsvjQ0jGBXFhM")
                    .setProjectId("surgedatabasefordrivers")
                    .build()
                driverApp = FirebaseApp.initializeApp(context, options, "DriverApp")!!
                driverDb = FirebaseFirestore.getInstance(driverApp)
                driverAppInitialized = true
                Log.d("FirebaseManager", "DriverApp initialized successfully")
            } catch (e: Exception) {
                Log.e("FirebaseManager", "Error initializing DriverApp: ${e.message}", e)
            }
        }
    }

    // Accessor for the default Firestore
    fun getPassengerFirestore(context: Context): FirebaseFirestore {
        if (!passengerAppInitialized) {
            initializePassengerApp(context)
        }
        return passengerDb
    }

    // Accessor for the secondary Firestore
    fun getDriverFirestore(context: Context): FirebaseFirestore {
        if (!driverAppInitialized) {
            initializeDriverApp(context)
        }
        return driverDb
    }

}

// When this button is clicked, add the ride to the database. This allows the drivers to see the ride on their app
fun addRideToDatabase(ride: Ride, driverFirestore: FirebaseFirestore) {
    Log.d("My Tag", "Add ride to database function started")

    try {
        driverFirestore.collection("Rides").document(ride.rideId).set(ride)
            .addOnSuccessListener {
                Log.d("My Tag", "Ride added to database successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("My Tag", "Error adding ride to database", exception)
            }
    } catch (e: Exception) {
        Log.e("My Tag", "Exception caught: ${e.message}", e)
    }
}
