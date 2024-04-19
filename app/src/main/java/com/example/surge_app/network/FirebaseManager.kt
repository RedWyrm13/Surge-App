package com.example.surge_app.network

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

object FirebaseManager {
    private val db = Firebase.firestore

    fun getDriverTest(driverName: String): Flow<Result<Map<String, Any>>> = flow {
        try {
            val snapshot = db.collection("users").document(driverName).get().await()
            if (snapshot.exists()) {
                emit(Result.success(snapshot.data!!))
            }
            else {
                emit(Result.failure(Exception("User not found")))
            }
        }
        catch (e: Exception) {
            emit(Result.failure(e))
        }

    }


}