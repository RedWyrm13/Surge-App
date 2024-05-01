package com.example.surge_app.viewModel

import android.location.Location

class RideViewModel(destinationViewModel: DestinationViewModel, locationViewModel: LocationViewModel) : DestinationViewModel() {

    // Initialize properties from DestinationViewModel with the values from the provided instance
    init {
        encodedPolyline = destinationViewModel.encodedPolyline
        distanceOfRoute = destinationViewModel.distanceOfRoute
        durationOfRoute = destinationViewModel.durationOfRoute

        // Get the latest latitude and longitude from the LocationViewModel
        val currentLatitude = locationViewModel.getLatestLatitude()
        val currentLongitude = locationViewModel.getLatestLongitude()

        // Now you have the latitude and longitude pair, you can use it as needed
        // For example, you can create a Location object
        val pickupLocation = Location("").apply {
            latitude = currentLatitude
            longitude = currentLongitude
        }

        // Now you can use the pickupLocation as needed in your RideViewModel
        // For example, you can pass it to the ride class
        // ride.pickupLocation = pickupLocation
    }
}
