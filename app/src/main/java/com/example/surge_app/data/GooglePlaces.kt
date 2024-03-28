    package com.example.surge_app.data

    import kotlinx.serialization.Serializable

    //This class holds the JSON response from the google places(new) API
    //Gave up on this API. Will come back later... maybe

    @Serializable
    data class DisplayName(
        val text: String,
        val languageCode: String?
    )

    @Serializable
    data class Place(
        val formattedAddress: String,
        val displayName: DisplayName
    )

    @Serializable
    data class GooglePlacesList(
        val places: List<Place>?
    )



