
package com.example.surge_app.network

import com.example.surge_app.data.ApiKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.DataOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

suspend fun postRequest(query: String = "Pizza"): String {
    return withContext(Dispatchers.IO) {
        val apiKey = ApiKey.apiKey
        val baseUrl = "https://places.googleapis.com/v1/places:searchText"
        val queryParam = "textQuery=$query"
        val fullUrl = "$baseUrl?$queryParam"

        val connection = URL(fullUrl).openConnection() as HttpURLConnection
        // Set the request method to POST
        connection.requestMethod = "POST"
        connection.setRequestProperty("Content-Type", "application/json")
        connection.setRequestProperty("X-Goog-Api-Key", apiKey)
        connection.setRequestProperty("X-Goog-FieldMask", "places.displayName,places.formattedAddress,places.priceLevel")

        // Build the request body JSON
        val requestBody = """
            {
                "textQuery": "$query",
                "priceLevels": ["PRICE_LEVEL_INEXPENSIVE", "PRICE_LEVEL_MODERATE"]
            }
        """.trimIndent()

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
