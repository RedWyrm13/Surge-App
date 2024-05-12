package com.example.surge_app.network

import android.content.Context
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseManager {

    private var passengerAppInitialized= false
    private lateinit var passengerApp: FirebaseApp
    private lateinit var passengerDb: FirebaseFirestore

    private var driverAppInitialized= false
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
            val options = FirebaseOptions.Builder()
                .setApplicationId("1:201633923016:android:617388de46b571d4774afd")
                .setApiKey("AIzaSyDsSGmGIHxU-BMU-R0XB3DsvjQ0jGBXFhM ")
                .setProjectId("surgedatabasefordrivers")
                .build()
            driverApp = FirebaseApp.initializeApp(context, options, "DriverApp" )
            driverDb = FirebaseFirestore.getInstance(driverApp)
            driverAppInitialized = true
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
    fun getDriverTest(context: Context, driverName: String): Flow<Result<Map<String, Any>>> = flow {
        try {
            if (!driverAppInitialized) initializeDriverApp(context) // Context needs to be provided correctly
            val snapshot = driverDb.collection("Drivers").document(driverName).get().await()
            if (snapshot.exists()) {
                emit(Result.success(snapshot.data!!))
            }
            else {
                emit(Result.failure(Exception("User not found")))
            }
        }
        catch (e: Exception) {
            Log.e("FirebaseManager", "Error fetching document: ${e.message}", e)
            emit(Result.failure(e))
        }

    }


}
