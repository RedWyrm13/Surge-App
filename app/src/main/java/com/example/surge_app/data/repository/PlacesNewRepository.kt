package com.example.surge_app.data.repository

import com.example.surge_app.data.GooglePlaces
import com.example.surge_app.data.Location
import com.example.surge_app.network.FieldMask
import com.example.surge_app.network.PlacesNewApiService
import retrofit2.http.GET
import retrofit2.http.Query

interface PlacesNewRepository {
    @GET("v1/places:searchNearby")
    suspend fun getDestination(@Query("X-Good-FieldMask") fieldMask:String = FieldMask.mask,
                               @Query("locationRestriction") locationRestriction: String): List<GooglePlaces>
}
