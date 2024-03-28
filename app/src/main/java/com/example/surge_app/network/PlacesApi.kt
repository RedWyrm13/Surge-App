package com.example.surge_app.network

import android.content.Context
import com.example.surge_app.ApiKey
import com.example.surge_app.data.AutocompleteResponse
import com.google.android.libraries.places.api.Places
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.IOException


object PlacesApiManager {
    private var initialized = false

    fun initializePlaces(context: Context) {
        if (!initialized) {
            try {
                val apiKeyInstance = ApiKey(context)
                val apiKeyValue = apiKeyInstance.apiKey
                Places.initialize(context, apiKeyValue)
                initialized = true
            } catch (e: IOException) {
                // Handle any exceptions when reading the API key from the properties file
                e.printStackTrace()
            }
        }
    }
}

interface PlacesApiService{

    @GET("maps/api/place/queryautocomplete/json")
    suspend fun getPlacesAutoComplete(
        @Query("input") input: String,
        @Query("key") apiKey: String
    ): AutocompleteResponse

}