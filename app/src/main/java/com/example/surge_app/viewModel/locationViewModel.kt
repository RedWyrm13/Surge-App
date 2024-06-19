package com.example.surge_app.viewModel
import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.ApiKey
import com.example.surge_app.data.RetrofitClient
import com.example.surge_app.network.GeocodingApiService
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    // Store the most recent location data in these variables
    private var latestLatitude: Double = 0.0
    private var latestLongitude: Double = 0.0

    // LiveData to observe changes in the location data
    val userLocation = MutableLiveData<Location>()

    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = LocationListener { location ->
        latestLatitude = location.latitude
        latestLongitude = location.longitude
        userLocation.postValue(location)
    }

    private val geocodingApiService = RetrofitClient.retrofit.create(GeocodingApiService::class.java)

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

    fun geocodeAddress(address: String): MutableLiveData<Location> {
        val resultLocation = MutableLiveData<Location>()
        viewModelScope.launch {
            try {
                val response = geocodingApiService.getCoordinates(address, ApiKey.apiKey)
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

    // Function to provide the most recent latitude
    fun getLatestLatitude(): Double {
        return latestLatitude
    }

    // Function to provide the most recent longitude
    fun getLatestLongitude(): Double {
        return latestLongitude
    }
    fun getLatestLocation(): com.example.surge_app.data.apiResponseData.Location {
        val currentLocation = com.example.surge_app.data.apiResponseData.Location(
            getLatestLatitude(),
            getLatestLongitude()
        )
        return currentLocation
    }

    override fun onCleared() {
        super.onCleared()
        locationManager.removeUpdates(locationListener)
    }
}
