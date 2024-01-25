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
    val mask = "places.formattedAddress,places.displayName"
}
/*https://maps.googleapis.com/maps/api/place/nearbysearch/json?key=AIzaSyDmcIQskDfuV7-mn33TTHCX8H635UNifGI&query=pizza&location=37.7749,-122.4194&radius=500&type=restaurant&fields=name,address,rating&language=en
is a working url and the getDestination call needs to follow this url format
*/

interface PlacesNewApiService {
//Base Url for this api is https://places.googleapis.com
    @Headers("Content-Type: application/json")
    @POST("/v1/places:searchText")
    suspend fun searchText(
        @Body request: SearchRequest,
        @Header("X-Goog-Api-Key") apiKey: String = ApiKey.apiKey,
        @Header("X-Goog-FieldMask") fieldMask: String = FieldMask.mask
        ): Response<ResponseBody>

}


