    package com.example.surge_app.data

    import android.util.Log
    import kotlinx.serialization.Contextual
    import kotlinx.serialization.Serializable
    import kotlinx.serialization.json.Json

    //This class holds the JSON response from the google places(new) API

    @Serializable
    data class DisplayName(
        val text: String,
        val languageCode: String
    )

    @Serializable
    data class Place(
        val formattedAddress: String,
        val displayName: DisplayName
    )

    @Serializable
    data class GooglePlacesList(
        val places: List<Place>
    )


    fun convertFromJsonStringToGooglePlacesList( jsonString: String): GooglePlacesList?{
        var googlePlacesList: GooglePlacesList? = null
        try {
            // Deserialize the JSON string into a GooglePlacesList object
            val json = Json { ignoreUnknownKeys = true } // Create a JSON instance
            googlePlacesList = json.decodeFromString<GooglePlacesList>(jsonString)

            // Now you can work with the GooglePlacesList object
            println("Display name: ${googlePlacesList.places[0].displayName.text}")
            println("Formatted address: ${googlePlacesList.places[0].formattedAddress}")
        } catch (e: Throwable) {
            // Handle JSON parsing exceptions here
            Log.d("My Tag", "${e.message}")
        }
        return googlePlacesList

    }