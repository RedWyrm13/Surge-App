package com.example.surge_app.viewModel

class RideViewModel(destinationViewModel: DestinationViewModel) : DestinationViewModel() {
    init {
        // Initialize properties from DestinationViewModel with the values from the provided instance
        encodedPolyline = destinationViewModel.encodedPolyline
        distanceOfRoute = destinationViewModel.distanceOfRoute
        durationOfRoute = destinationViewModel.durationOfRoute
    }

}
