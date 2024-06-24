package com.example.surge_app.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.surge_app.data.Driver
import com.example.surge_app.data.Ride
import com.example.surge_app.data.apiResponseData.Location
import com.example.surge_app.data.repositories.RideRepoImpl
import kotlinx.coroutines.launch

class RideViewModel(rideRepoImpl: RideRepoImpl): ViewModel(){
    val encodedPolyline = rideRepoImpl.fetchEncodedPolyline()
    val distanceOfRoute = rideRepoImpl.fetchDistanceOfRoute()
    val durationOfRoute = rideRepoImpl.fetchDurationOfRoute()


    private val rideRepoImpl: RideRepoImpl = rideRepoImpl
    fun addRideToDatabase(ride: Ride) {
        viewModelScope.launch {
            rideRepoImpl.addRideToDatabase(ride)
        }

    }

    fun fetchDriversInArea(pickupLocation: Location): List<Driver> {
        var driverList = listOf<Driver>()
        Log.d("MyTag_RideViewModel", "Before ViewModel")
        viewModelScope.launch {
            Log.d("MyTag_RideViewModel", "During ViewModel")

            driverList = rideRepoImpl.fetchDriversInArea(pickupLocation)
        }
        Log.d("MyTag_RideViewModel", "After ViewModel")

        return driverList
    }

}
