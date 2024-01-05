package com.example.surge_app.viewModel

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.surge_app.GeocodingApiService
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    val userLocation = MutableLiveData<Location>()
    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = LocationListener { location ->
        userLocation.postValue(location)
    }
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val geocodingApiService = retrofit.create(GeocodingApiService::class.java)

    init {
        fetchLocation()
    }

    private fun fetchLocation() {
        viewModelScope.launch {
            // Check and request location permissions here
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0L,
                    0f,
                    locationListener
                )
            } catch (e: SecurityException) {
                // Handle the security exception
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationManager.removeUpdates(locationListener)
    }
    fun geocodeAddress(address: String): MutableLiveData<Location> {
        val resultLocation = MutableLiveData<Location>()
        viewModelScope.launch {
            try {
                // Here we use the API key directly in the function call
                val response = geocodingApiService.getCoordinates(address, "AIzaSyC5ejGE4cTIsppRPCQLJDoU92BbPDLG8v8")
                if (response.status == "OK" && response.results.isNotEmpty()) {
                    val location = response.results[0].geometry.location
                    resultLocation.postValue(Location("").apply {
                        latitude = location.lat
                        longitude = location.lng
                    })
                } else {
                    // Handle no result found or error
                }
            } catch (e: Exception) {
                // Handle network or other errors
            }
        }
        return resultLocation
    }
}
