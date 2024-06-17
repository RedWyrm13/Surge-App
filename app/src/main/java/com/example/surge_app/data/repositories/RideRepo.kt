package com.example.surge_app.data.repositories

import android.location.Location
import android.util.Log
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.GeocodingResponse
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.Ride
import com.example.surge_app.data.RouteResponse
import com.example.surge_app.network.FirebaseManager
import com.example.surge_app.network.GeocodingApiService
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import org.json.JSONObject
import retrofit2.http.GET
import retrofit2.http.Query
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

//Might have to add location view model information to you to pass to the ride screen. We shall see
interface RideRepo {

    suspend fun routesPostRequest(geocodingResponse: GeocodingResponse, userLocation: Location?): String
    fun addRideToDatabase(ride: Ride)

    @GET("maps/api/geocode/json")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") apiKey: String
    ): GeocodingResponse

    fun fetchEncodedPolyline(): String?
    fun fetchDistanceOfRoute(): Int
    fun fetchDurationOfRoute(): String
}

class RideRepoImpl : RideRepo {
    private val geocodingApiService = RetrofitClient.retrofit.create(GeocodingApiService::class.java)
    var encodedPolyline: String? = null
    var distanceOfRoute: Int = 0
    var durationOfRoute: String = ""

    override suspend fun routesPostRequest(
        geocodingResponse: GeocodingResponse,
        userLocation: Location?
    ): String {
        return withContext(Dispatchers.IO) {
            val apiKey = ApiKey.apiKey
            val urlString = "https://routes.googleapis.com/directions/v2:computeRoutes"
            val jsonData = """
        {
          "origin":{
            "location":{
              "latLng":{
                "latitude": ${userLocation!!.latitude},
                "longitude": ${userLocation.longitude}
              }
            }
          },
          "destination":{
            "location":{
              "latLng":{
                "latitude": ${geocodingResponse.results[0].geometry.location.lat},
                "longitude": ${geocodingResponse.results[0].geometry.location.lng}
              }
            }
          },
          "travelMode": "DRIVE",
          "routingPreference": "TRAFFIC_AWARE",
          "computeAlternativeRoutes": false,
          "routeModifiers": {
            "avoidTolls": false,
            "avoidHighways": false,
            "avoidFerries": false
          },
          "languageCode": "en-US",
          "units": "IMPERIAL"
        }
        """.trimIndent()

            val url = URL("$urlString?key=$apiKey")
            val connection = url.openConnection() as HttpURLConnection
            try {
                connection.requestMethod = "POST"
                connection.setRequestProperty("Content-Type", "application/json")
                connection.setRequestProperty(
                    "X-Goog-FieldMask",
                    "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline,routes.warnings,routes.description"
                )
                connection.doOutput = true

                val outputStreamWriter = OutputStreamWriter(connection.outputStream)
                outputStreamWriter.write(jsonData)
                outputStreamWriter.flush()

                val responseCode = connection.responseCode

                val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                    connection.inputStream.bufferedReader().use { it.readText() }
                } else {
                    connection.errorStream.bufferedReader().use { it.readText() }
                }

                // Parse the response using the helper function
                val routeResponse = convertFromJsonStringToRoutesResponse(response)
                if (routeResponse.routes.isNotEmpty()) {
                    val route = routeResponse.routes[0]
                    encodedPolyline = route.polyline.encodedPolyline
                    distanceOfRoute = route.distanceMeters
                    durationOfRoute = route.duration
                }

                Log.d("My Response From Routes", response)

                response
            } catch (e: Exception) {
                // Log the error
                Log.e("MyTag", "Error: ${e.message}", e)
                ""
            } finally {
                connection.disconnect()
            }
        }
    }


    override fun addRideToDatabase(ride: Ride) {
        Log.d("My Tag", "Add ride to database function started")

        val driverFirestore: FirebaseFirestore = FirebaseManager.getDriverFirestore()

        try {
            driverFirestore.collection("Rides").document(ride.rideId).set(ride)
                .addOnSuccessListener {
                    Log.d("My Tag", "Ride added to database successfully")
                }
                .addOnFailureListener { exception ->
                    Log.e("My Tag", "Error adding ride to database", exception)
                }
        } catch (e: Exception) {
            Log.e("My Tag", "Exception caught: ${e.message}", e)
        }
    }

    override suspend fun getCoordinates(
        address: String,
        apiKey: String
    ): GeocodingResponse = geocodingApiService.getCoordinates(address, apiKey)

    override fun fetchEncodedPolyline(): String? = encodedPolyline

    override fun fetchDistanceOfRoute(): Int = distanceOfRoute

    override fun fetchDurationOfRoute(): String = durationOfRoute

    fun convertFromJsonStringToRoutesResponse(jsonString: String): RouteResponse {
        return Json.decodeFromString<RouteResponse>(jsonString)
    }
}
