package com.example.surge_app.network

import android.util.Log
import com.example.surge_app.data.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

suspend fun routesPostRequest(): String {
    return withContext(Dispatchers.IO) {
        Log.d("Response", "routesPostRequest is running")
        val apiKey = ApiKey.apiKey
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
          "departureTime": "2023-10-15T15:01:23.045123456Z",
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

        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("X-Goog-Api-Key", apiKey)
        connection.setRequestProperty("Authorization","ya29.a0Ad52N39A7n8-kFoUB2mIHyFdburrSMqTJkYaWcSN8mmeWK204TM9t7AxUIXhIfflL89M6YEQyRylk8l7wevmHNDGeu_Xhm_w_uzx9tdt5uis7gIcmWdT39JbQpSQzlrMXWUjcl7I07sz_LLDx4Kx7VBbmxJ81YcHJuTVaCgYKAQwSARASFQHGX2MijYl6xf2kY0ye24B8cH-1Og0171")
        connection.setRequestProperty("X-Goog-User-Project","surge-f7068")
        connection.setRequestProperty(
            "X-Goog-FieldMask",
            "routes.duration,routes.distanceMeters,routes.polyline.encodedPolyline"
        )
        connection.doOutput = true

        val outputStreamWriter = OutputStreamWriter(connection.outputStream)
        outputStreamWriter.write(jsonData)
        outputStreamWriter.flush()

        val responseCode = connection.responseCode
        println("Response Code: $responseCode")

        val response = connection.inputStream.bufferedReader().use { it.readText() }
        Log.d("Response", response)

        connection.disconnect()

        response.toString()
    }
}