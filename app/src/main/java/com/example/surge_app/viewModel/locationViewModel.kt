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
import com.example.surge_app.data.repositories.GeocodingRepoImpl
import com.example.surge_app.network.GeocodingApiService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    // Store the most recent location data in these variables
    private var latestLatitude: Double = 0.0
    private var latestLongitude: Double = 0.0

    private val geocodingRepo = GeocodingRepoImpl(RetrofitClient.retrofit.create(GeocodingApiService::class.java))

    // LiveData to observe changes in the location data
    val userLocation = MutableLiveData<Location>()

    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

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

    fun geocodeAddress(address: String): MutableLiveData<Location> {
        val resultLocation = MutableLiveData<Location>()
        viewModelScope.launch {
            try {
                val response = geocodingRepo.getCoordinates(address)
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

    fun reverseGeocodeAddress(latlng: String = formatLocationString(getLatestLocation().toString())): String {
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
    private fun getLatestLatitude(): Double {
        return latestLatitude
    }

    // Function to provide the most recent longitude
    private fun getLatestLongitude(): Double {
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

fun formatLocationString(locationString: String): String {
    val latLngRegex = Regex("""Location\(lat=(-?\d+\.\d+), lng=(-?\d+\.\d+)\)""")
    val matchResult = latLngRegex.find(locationString)

    return if (matchResult != null) {
        val (lat, lng) = matchResult.destructured
        "$lat,$lng"
    } else {
        throw IllegalArgumentException("Invalid location string format")
    }
}
