package com.example.surge_app.ui.theme.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.Driver
import com.example.surge_app.data.Ride
import com.example.surge_app.data.SimpleLocation
import com.example.surge_app.data.repositories.RideRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RideViewModel(val rideRepoImpl: RideRepoImpl): ViewModel(){
    val encodedPolyline = rideRepoImpl.fetchEncodedPolyline()
    val distanceOfRoute = rideRepoImpl.fetchDistanceOfRoute()
    val durationOfRoute = rideRepoImpl.fetchDurationOfRoute()

    private val _potentialDrivers = MutableStateFlow<List<Driver>>(emptyList())
    val potentialDrivers: StateFlow<List<Driver>> = _potentialDrivers
    var pickupLocation: SimpleLocation? = null

    var ride: Ride? = null
    fun addRideToDatabase() {
        viewModelScope.launch {
            try {
                Log.e("MyTag_RideViewModel", "addRideToDatabase: $ride. If this value is null, that is why it is crashing.")
                rideRepoImpl.addRideToDatabase(ride!!)
            }
            catch (e: Exception) {
                Log.e("MyTag_RideViewModel", "Exception caught: ${e.message}", e)
            }

        }


    }
    fun addRequestedDriverToList(driverId: String) {
        viewModelScope.launch {
            try {
                rideRepoImpl.addRequestedDriverToList(ride!!.rideId, driverId)
            }
            catch (e: Exception) {
                Log.e("MyTag_RideViewModel", "Exception caught: ${e.message}", e)
            }
        }
    }

    fun updatePickupLocation(pickupLocation: SimpleLocation) {
        this.pickupLocation = pickupLocation
    }

    fun fetchDriversInArea() {
        viewModelScope.launch {
            try {
                Log.e("MyTag_RideViewModel", "fetchDriversInArea: $pickupLocation. If this value is null, that is why it is crashing.")
                _potentialDrivers.value = rideRepoImpl.fetchNearbyDrivers(pickupLocation!!)
            } catch (e: Exception) {
                Log.e("MyTag_RideViewModel", "Exception caught: ${e.message}", e)
            }

        }

    }
}

