package com.example.surge_app.data.apiResponseData

import com.google.gson.annotations.SerializedName

data class ReverseGeocodingResponse(
    val results: List<ReverseGeocodingResult>
)

data class ReverseGeocodingResult(
    @SerializedName("formatted_address") val formattedAddress: String
)
