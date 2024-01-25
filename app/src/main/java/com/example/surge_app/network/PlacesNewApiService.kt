package com.example.surge_app.network

import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.SearchRequest
import com.squareup.okhttp.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

object FieldMask{
    const val mask = "places.formattedAddress,places.displayName"
}

interface PlacesNewApiService {
//Base Url for this api is https://places.googleapis.com
    @Headers("Content-Type: application/json",
        "X-Goog-Api-Key: ${ApiKey.apiKey}",
        "X-Goog-FieldMask: ${FieldMask.mask}"
    )
    @POST("v1/places:searchText")
    suspend fun searchText(@Body textQuery: String): Response<ResponseBody>

}


