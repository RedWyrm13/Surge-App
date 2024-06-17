package com.example.surge_app.viewModel

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.surge_app.data.repositories.RideRepoImpl

class RideViewModel(rideRepoImpl: RideRepoImpl): ViewModel(){
    val encodedPolyline = rideRepoImpl.fetchEncodedPolyline()
    val distanceOfRoute = rideRepoImpl.fetchDistanceOfRoute()
    val durationOfRoute = rideRepoImpl.fetchDurationOfRoute()

}
