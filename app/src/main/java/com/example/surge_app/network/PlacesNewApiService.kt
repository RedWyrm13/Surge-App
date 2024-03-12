
package com.example.surge_app.network

import android.util.Log
import com.example.surge_app.data.ApiKey
import com.example.surge_app.viewModel.LocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

suspend fun placesPostRequest(query: String, locationViewModel: LocationViewModel): String {

    return withContext(Dispatchers.IO) {
        val queryNoSpaces = query.replace(" ","")
        val apiKey = ApiKey.apiKey
        val baseUrl = "https://places.googleapis.com/v1/places:searchText"
        val queryParam = "textQuery=$queryNoSpaces"
        val fullUrl = "$baseUrl?$queryParam"

        Log.d("My Tag", fullUrl)

        val connection = URL(fullUrl).openConnection() as HttpURLConnection
        // Set the request method to POST
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("X-Goog-Api-Key", apiKey)
        connection.setRequestProperty("X-Goog-FieldMask", "places.displayName,places.formattedAddress")

        // Build the request body JSON
        val requestBody = """
  {
   "textQuery" : "$query",
    "locationBias": {
        "circle": {
            "center": {
                "latitude": 37,
                "longitude": -36
      },
            "radius": 50000
    }
  }
 }
   """.trimIndent()
        Log.d("My Tag", requestBody)

        // Enable input/output streams for the request
        connection.doOutput = true
        val outputStream = DataOutputStream(connection.outputStream)

        // Write the request body to the output stream
        outputStream.writeBytes(requestBody)
        outputStream.flush()
        outputStream.close()

        // Read the response data
        val responseReader = BufferedReader(InputStreamReader(connection.inputStream))
        var inputLine: String?
        val response = StringBuilder()

        while (responseReader.readLine().also { inputLine = it } != null) {
            response.append(inputLine)
        }
        responseReader.close()

        // Return the response data as a String
        response.toString()
    }
}
