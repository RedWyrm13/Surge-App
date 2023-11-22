package com.example.surge_app.viewModel

import android.app.Application
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LocationViewModel(application: Application) : AndroidViewModel(application) {
    val userLocation = MutableLiveData<Location>()
    private val locationManager = application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationListener = LocationListener { location ->
        userLocation.postValue(location)
    }

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
}
