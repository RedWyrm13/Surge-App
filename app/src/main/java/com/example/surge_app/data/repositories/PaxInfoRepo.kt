package com.example.surge_app.data.repositories

import android.util.Log
import com.example.surge_app.data.Pax
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

interface PaxInfoRepo {

    suspend fun setAddPaxInfo(pax: Pax): Boolean
    suspend fun setUpdatePaxInfo(pax: Pax): Boolean
    suspend fun getPaxInfo(paxId: String): Pax?
}

class PaxInfoRepoImpl(val driverFirestore: FirebaseFirestore): PaxInfoRepo {
    override suspend fun setAddPaxInfo(pax: Pax): Boolean {
        return try{
            driverFirestore.collection("Pax").document(pax.paxId).set(pax).await()
            true
        } catch (e: Exception){
            Log.e("MyTag_PaxInfoRepoImpl", "Error adding pax info: ${e.message}")
            false
        }
    }

    //I want to use the .update function instead of the set function to update specified fields.
    //I need to pass the fields to update as well as their values to this function.
    override suspend fun setUpdatePaxInfo(pax: Pax): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun getPaxInfo(paxId: String): Pax? {
        return try {
            val documentSnapshot = driverFirestore.collection("Pax").document(paxId).get().await()
            documentSnapshot.toObject(Pax::class.java)
        } catch (e: Exception) {
            Log.e("MyTag_PaxInfoRepoImpl", "Error getting pax info: ${e.message}")
            null
        }
    }
}