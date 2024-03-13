package com.example.surge_app.network

import android.util.Log
import com.example.surge_app.data.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL
suspend fun routesPostRequest(): String {
    return withContext(Dispatchers.IO) {
        Log.d("MyTag", "Coroutine starting!")

        val apiKey = "YOUR_API_KEY"
        val urlString = "https://routes.googleapis.com/directions/v2:computeRoutes"
        val jsonData = """
            {
              "origin":{
                "location":{
                  "latLng":{
                    "latitude": 37.419734,
                    "longitude": -122.0827784
                  }
                }
              },
              "destination":{
                "location":{
                  "latLng":{
                    "latitude": 37.417670,
                    "longitude": -122.079595
                  }
                }
              },
              "travelMode": "DRIVE",
              "routingPreference": "TRAFFIC_AWARE",
              "departureTime": "2024-03-13T15:01:23.045123456Z",
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

        val accessToken = ApiKey.accessToken
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        try {
            connection.requestMethod = "POST"
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-Goog-Api-Key", apiKey)
            connection.setRequestProperty("Authorization", "Bearer $accessToken")
            connection.setRequestProperty("X-Goog-User-Project", ApiKey.projectId)
            connection.setRequestProperty(
                "X-Goog-FieldMask",
                "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
            )
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
