package com.example.surge_app.network

import android.location.Location
import android.util.Log
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.GeocodingResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

// This file makes the PostRequest to the google routes API. This response is used to draw the route on the google map
// from the user's current location to the input destination.

suspend fun routesPostRequest(geocodingResponse: GeocodingResponse, userLocation: Location?): String {
    return withContext(Dispatchers.IO) {
        val apiKey = ApiKey.apiKey
        val urlString = "https://routes.googleapis.com/directions/v2:computeRoutes"
        val jsonData = """
            {
              "origin":{
                "location":{
                  "latLng":{
                    "latitude": ${userLocation!!.latitude},
                    "longitude": ${userLocation!!.longitude}
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
            connection.setRequestProperty("X-Goog-FieldMask", "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline")
            connection.doOutput = true

            val outputStreamWriter = OutputStreamWriter(connection.outputStream)
            outputStreamWriter.write(jsonData)
            outputStreamWriter.flush()

            val responseCode = connection.responseCode
            Log.d("MyTag", "Response Code: $responseCode")

            val response = if (responseCode == HttpURLConnection.HTTP_OK) {
                connection.inputStream.bufferedReader().use { it.readText() }
            } else {
                connection.errorStream.bufferedReader().use { it.readText() }
            }
            Log.d("MyTag", "Response: $response")

            response
        } catch (e: Exception) {
            Log.e("MyTag", "Error: ${e.message}", e)
            ""
        }
    }
}
