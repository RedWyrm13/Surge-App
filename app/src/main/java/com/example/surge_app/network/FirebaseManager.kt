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
    fun fetchPassengerFirestore(): FirebaseFirestore {
        return passengerDb
    }

    // Accessor for the secondary Firestore
    fun fetchDriverFirestore(): FirebaseFirestore {
        return driverDb
    }
}



