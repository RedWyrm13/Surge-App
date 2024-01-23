package com.example.surge_app.network

import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.Center
import com.example.surge_app.data.Circle
import com.example.surge_app.data.GooglePlaces
import com.example.surge_app.data.LocationRestriction
import retrofit2.http.GET
import retrofit2.http.Query

object FieldMask{
    val mask = "places.formattedAddress,places.displayName"
    //We need to refactor the geocoding api so that we can use the coordinates of the
    //user defined destination for the center of the circle
    val locationRestriction = LocationRestriction(
        circle = Circle(
            center = Center(
                latitude = 0.0, // Default latitude
                longitude = 0.0 // Default longitude
            ),
            radius = 25.0 // Default radius
        )    )

}

interface PlacesNewApiService {


    @GET("v1/places:nearbyText")
    suspend fun getDestination(@Query("fields") fieldMask:String = FieldMask.mask,
                               @Query("locationRestriction") locationRestriction: LocationRestriction = FieldMask.locationRestriction,
                               @Query("query") address: String,
                               @Query("key") apiKey: String = ApiKey.apiKey): List<GooglePlaces>
}
