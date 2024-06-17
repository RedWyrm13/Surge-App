package com.example.surge_app.network

import android.util.Log
import com.example.surge_app.data.Ride
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

object FirebaseManager {

    private val passengerApp: FirebaseApp by lazy {
        FirebaseApp.getInstance()
    }

    private val passengerDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance(passengerApp)
    }

    private val driverApp: FirebaseApp by lazy {
        FirebaseApp.getInstance("DriverApp")
    }

    private val driverDb: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance(driverApp)
    }

    // Accessor for the default Firestore
    fun getPassengerFirestore(): FirebaseFirestore {
        return passengerDb
    }

    // Accessor for the secondary Firestore
    fun getDriverFirestore(): FirebaseFirestore {
        return driverDb
    }
}


fun addRideToDatabase(ride: Ride) {
    Log.d("My Tag", "Add ride to database function started")

    val driverFirestore: FirebaseFirestore = FirebaseManager.getDriverFirestore()

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
