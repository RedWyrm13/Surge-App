package com.example.surge_app.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{    // Create a Retrofit instance for making API requests to Google's Geocoding service
    var baseUrl = "https://maps.googleapis.com/"
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun changeRetrofitUrl(baseUrl: String){
        RetrofitClient.baseUrl = baseUrl
    }
}

