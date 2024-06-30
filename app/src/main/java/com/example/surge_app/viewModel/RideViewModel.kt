package com.example.surge_app.viewModel

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    private val rideRepoImpl: RideRepoImpl = rideRepoImpl
    fun addRideToDatabase(ride: Ride) {
        viewModelScope.launch {
            rideRepoImpl.addRideToDatabase(ride)

        }


    }

    private val _driverList = MutableLiveData<List<Driver>>()
    val driverList: LiveData<List<Driver>> get() = _driverList

    fun fetchDriversInArea(pickupLocation: SimpleLocation) {
        viewModelScope.launch {
            try {
                val drivers = rideRepoImpl.fetchDriversInArea(pickupLocation)
                _driverList.postValue(drivers)

            } catch (e: Exception) {
            }

        }
    }
}

