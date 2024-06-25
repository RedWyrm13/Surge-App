package com.example.surge_app.network

import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.apiResponseData.GeocodingResponse
import com.example.surge_app.data.apiResponseData.ReverseGeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocodingApiService {
    // This specifies the endpoint for the url performing the request. This is added to the bsae URL in the retrofit object
    // to make a request at https://maps.googleapis.com/maps/api/geocode/json which is the full url
    @GET("maps/api/geocode/json")
    suspend fun getCoordinates(
        //These parameters marked with the @Query annotation are added onto the end of the full url
        @Query("address") address: String,
        @Query("key") apiKey: String = ApiKey.apiKey
    ): GeocodingResponse

    @GET("maps/api/geocode/json")
    suspend fun reverseGeocode(
        @Query("latlng") latLng: String,
        @Query("key") apiKey: String = ApiKey.apiKey
    ): ReverseGeocodingResponse

    //Making the @GET request at this URL returns a JavaScriptObjectNotation (JSON) response which has to be deserialized into a kotlin object
    //The deserialization process is fairly straight forward. See this codelab for more information https://developer.android.com/codelabs/basic-android-kotlin-compose-getting-data-internet?continue=https%3A%2F%2Fdeveloper.android.com%2Fcourses%2Fpathways%2Fandroid-basics-compose-unit-5-pathway-1%23codelab-https%3A%2F%2Fdeveloper.android.com%2Fcodelabs%2Fbasic-android-kotlin-compose-getting-data-internet#0
}