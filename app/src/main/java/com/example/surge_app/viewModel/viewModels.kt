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

// Creates a class called LocationViewModel which is used when rendering the view of the user's
// current location.
class LocationViewModel(application: Application) : AndroidViewModel(application) {
    // Creates a variable that defines and updates the user's current location
    val userLocation = MutableLiveData<Location>()
    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    // Defines a LocationListener to receive updates on the user's location
    private val locationListener = LocationListener { location ->
        userLocation.postValue(location)
    }

    // Create a Retrofit instance for making API requests to Google's Geocoding service
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://maps.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create a GeocodingApiService using Retrofit
    private val geocodingApiService = retrofit.create(GeocodingApiService::class.java)

    // This is the constructor block, it gets executed when an instance of LocationViewModel is created
    init {
        fetchLocation() // Calls the function to start fetching user's location
    }

    // Function to request and listen for the user's location updates
    private fun fetchLocation() {
        viewModelScope.launch {
            // Check and request location permissions here
            try {
                locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // Using GPS for location updates
                    0L, // Minimum time interval between updates (0 milliseconds)
                    0f, // Minimum distance interval between updates (0 meters)
                    locationListener // Listener to handle location updates
                )
            } catch (e: SecurityException) {
                // Handle the security exception if permissions are denied
            }
        }
    }

    // Function to geocode an address and return its location as a MutableLiveData
    fun geocodeAddress(address: String): MutableLiveData<Location> {
        val resultLocation = MutableLiveData<Location>() // Create MutableLiveData for the result
        viewModelScope.launch {
            try {
                // Use the GeocodingApiService to fetch coordinates for the given address
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
        return resultLocation // Return the MutableLiveData with geocoded location
    }

    // This method is called when the ViewModel is no longer in use, and it removes location updates
    override fun onCleared() {
        super.onCleared()
        locationManager.removeUpdates(locationListener)
    }
}
