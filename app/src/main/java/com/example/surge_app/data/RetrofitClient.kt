
package com.example.surge_app.data

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Create a Retrofit instance for making API requests to Google's Geocoding service
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
    fun changeRetrofitUrl(baseUrl: String){
        RetrofitClient.baseUrl = baseUrl
    }
}