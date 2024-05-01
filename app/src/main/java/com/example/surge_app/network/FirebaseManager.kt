package com.example.surge_app.network

import android.annotation.SuppressLint
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseManager {
    private val db = Firebase.firestore

    fun getDriverTest(driverName: String): Flow<Result<Map<String, Any>>> = flow {
        Log.d("My Tag", "getDriverTest function is running")
        try {
            val snapshot = db.collection("Drivers").document(driverName).get().await()
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