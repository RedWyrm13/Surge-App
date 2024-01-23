package com.example.surge_app.network

import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.GooglePlaces
import retrofit2.http.GET
import retrofit2.http.Query

object FieldMask{
    val mask = "places.formattedAddress,places.displayName"
    val locationRestriction = 500.00
}

interface PlacesNewApiService {


    @GET("v1/places:nearbyText")
    suspend fun getDestination(@Query("fields") fieldMask:String = FieldMask.mask,
                               @Query("locationRestriction") locationRestriction: Double = FieldMask.locationRestriction,
                               @Query("query") address: String,
                               @Query("key") apiKey: String = ApiKey.apiKey): List<GooglePlaces>
}
