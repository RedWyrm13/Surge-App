
package com.example.surge_app.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Create a Retrofit instance for making API requests to Google's Geocoding service

/*
To use this you need to first create an interface (see com\example\surge_app\network\GeocodingApiService.kt as template)
After you have the appropriate interface you need to create a retrofit client with the interface as shown in the example below:
     private val geocodingApiService = RetrofitClient.retrofit.create(GeocodingApiService::class.java)
     In the line above, GeocodingApiService is the name of the interface that was created.

*/

object RetrofitClient{

    private val client = OkHttpClient.Builder().build()

    var baseUrl = "https://maps.googleapis.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}