package com.example.surge_app.data.repositories

import com.example.surge_app.data.apiResponseData.GeocodingResponse
import com.example.surge_app.data.apiResponseData.ReverseGeocodingResponse
import com.example.surge_app.network.GeocodingApiService

interface GeocodingRepo {
    suspend fun getCoordinates(address: String): GeocodingResponse

    suspend fun reverseGeocode(latLng: String): ReverseGeocodingResponse
}
class GeocodingRepoImpl(val geocodingApiService: GeocodingApiService): GeocodingRepo {
    override suspend fun getCoordinates(address: String): GeocodingResponse {
        return geocodingApiService.getCoordinates(address)
    }

    override suspend fun reverseGeocode(latLng: String): ReverseGeocodingResponse {
        return geocodingApiService.reverseGeocode(latLng)
    }
}