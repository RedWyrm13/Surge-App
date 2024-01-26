package com.example.surge_app.data

import android.util.Log
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

//This class holds the JSON response from the google places(new) API
@Serializable
data class GooglePlaces(
    val displayName: DisplayName,
    val formattedAddress: String,
)

data class DisplayName(
    val text: String,
    val languageCode: String
)

data class GooglePlacesList(val googlePlaces: List<GooglePlaces>)


fun convertFromJsonStringToGooglePlacesList( jsonString: String): GooglePlacesList?{
    var googlePlacesList: GooglePlacesList? = null
    try {
        // Deserialize the JSON string into a GooglePlacesList object
        val json = Json { ignoreUnknownKeys = true } // Create a JSON instance
        googlePlacesList = json.decodeFromString<GooglePlacesList>(jsonString)

        // Now you can work with the GooglePlacesList object
        println("Display name: ${googlePlacesList.googlePlaces[0].displayName.text}")
        println("Formatted address: ${googlePlacesList.googlePlaces[0].formattedAddress}")
    } catch (e: Throwable) {
        // Handle JSON parsing exceptions here
        Log.d("My Tag", "${e.message}")
    }
    return googlePlacesList

}