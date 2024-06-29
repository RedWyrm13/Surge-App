package com.example.surge_app.viewModel

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.data.SimpleLocation
import com.example.surge_app.data.repositories.GeocodingRepoImpl
import com.example.surge_app.network.GeocodingApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    // Store the most recent location data in these variables
    private var latestLatitude: Double = 0.0
    private var latestLongitude: Double = 0.0

    private val geocodingRepo =
        GeocodingRepoImpl(RetrofitClient.retrofit.create(GeocodingApiService::class.java))

    // LiveData to observe changes in the location data
    val userLocation = MutableLiveData<Location>()

    private val locationManager =
        application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = LocationListener { location ->
        latestLatitude = location.latitude
        latestLongitude = location.longitude
        userLocation.postValue(location)
    }

    init {
        fetchLocation()
    }

    private fun fetchLocation() {
        viewModelScope.launch {
            try {
                if (ContextCompat.checkSelfPermission(
                        getApplication(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                    ) == android.content.pm.PackageManager.PERMISSION_GRANTED
                ) {
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0L,
                        0f,
                        locationListener
                    )
                } else {
                    // Handle permission not granted
                }
            } catch (e: SecurityException) {
                // Handle security exception if permissions are denied
            }
        }
    }

    fun GeocodeAddress(address: String): SimpleLocation {
        var simpleLocation = SimpleLocation(10.0, 10.0)
        runBlocking {
            Log.d("MyTag_locationViewModel", "Before: " + simpleLocation.toString())
            simpleLocation = geocodeAddress(address)
            Log.d("MyTag_locationViewModel", "After: " + simpleLocation.toString())

        }
        return simpleLocation
    }

    suspend fun geocodeAddress(address: String): SimpleLocation {
        val resultLocation = MutableLiveData<Location>()
        var simpleLocation = SimpleLocation(0.0, 0.0)
        try {
            val response = withContext(Dispatchers.IO) { geocodingRepo.getCoordinates(address) }
            if (response.status == "OK" && response.results.isNotEmpty()) {
                val location = response.results[0].geometry.location
                resultLocation.postValue(Location("").apply {
                    latitude = location.lat
                    longitude = location.lng
                })
                simpleLocation = SimpleLocation(location.lat, location.lng)
            } else {
                // Handle no result found or error
            }
        } catch (e: Exception) {
            Log.e("MyTag_locationViewModel", "Error: ${e.message}")

        }
        return simpleLocation
    }

    fun reverseGeocodeAddress(latlng: String = getLatestLatitude().toString() + "," + getLatestLongitude().toString()): String {
        Log.d("MyTag_locationViewModel", latlng)
        var address = ""
        runBlocking {
            try {
                val response = geocodingRepo.reverseGeocode(latlng)
                if (response.results.isNotEmpty()) {
                    address = response.results[0].formattedAddress
                } else {
                    // Handle no result found or error
                    //User can type in address manually
                }
            } catch (e: Exception) {
                Log.e("MyTag_locationViewModel", "Error: ${e.message}")
            }
        }

        return address
    }


    // Function to provide the most recent latitude
    fun getLatestLatitude(): Double {
        return latestLatitude
    }

    // Function to provide the most recent longitude
    fun getLatestLongitude(): Double {
        return latestLongitude
    }

    fun getLatestLocation(): Location {
        val currentLocation =
            Location("provider") // Replace "provider" with your actual provider name
        currentLocation.latitude = getLatestLatitude()
        currentLocation.longitude = getLatestLongitude()
        return currentLocation
    }

    override fun onCleared() {
        super.onCleared()
        locationManager.removeUpdates(locationListener)
    }
}
