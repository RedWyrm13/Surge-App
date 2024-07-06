package com.example.surge_app.ui.theme.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.Driver
import com.example.surge_app.data.Ride
import com.example.surge_app.data.SimpleLocation
import com.example.surge_app.data.repositories.RideRepoImpl
import kotlinx.coroutines.launch

class RideViewModel(rideRepoImpl: RideRepoImpl): ViewModel(){
    val encodedPolyline = rideRepoImpl.fetchEncodedPolyline()
    val distanceOfRoute = rideRepoImpl.fetchDistanceOfRoute()
    val durationOfRoute = rideRepoImpl.fetchDurationOfRoute()

    var potentialDrivers = listOf<Driver>()
    var pickupLocation: SimpleLocation? = null

    private val rideRepoImpl: RideRepoImpl = rideRepoImpl
    fun addRideToDatabase(ride: Ride) {
        viewModelScope.launch {
            rideRepoImpl.addRideToDatabase(ride)

        }


    }

    fun fetchDriversInArea() {
        viewModelScope.launch {
            try {
                Log.e("MyTag_RideViewModel", "fetchDriversInArea: $pickupLocation. If this value is null, that is why it is crashing.")
                potentialDrivers = rideRepoImpl.fetchNearbyDrivers(pickupLocation!!)
            } catch (e: Exception) {
                Log.e("MyTag_RideViewModel", "Exception caught: ${e.message}", e)
            }

        }

    }
}

