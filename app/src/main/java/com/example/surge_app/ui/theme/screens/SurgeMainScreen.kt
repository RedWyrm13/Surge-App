package com.example.surge_app.ui.theme.screens

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.AutocompleteTextView
import com.example.surge_app.ui.theme.MainScreenBottomBar
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel

@Composable
fun SurgeMainScreen(
    locationViewModel: LocationViewModel,
    destinationViewModel: DestinationViewModel = viewModel(),
    onRideButtonClicked: () -> Unit
) {

    // State variables
    val destination by remember { mutableStateOf("") }

    var geocodedLocation by remember { mutableStateOf<Location?>(null) }

    val userLocation by locationViewModel.userLocation.observeAsState()

    // Default location
    val defaultLocation = Location("").apply {
        latitude = 36.114647
        longitude = -115.172813
    }

    // Location initialization check
    val isLocationInitialized = userLocation != null && userLocation != defaultLocation
    val rideRepoImpl = RideRepoImpl()

    // LaunchedEffect to observe destination changes
    LaunchedEffect(destination) {
        if (destination.isNotEmpty()) {
            locationViewModel.geocodeAddress(destination).observeForever {
                geocodedLocation = it
            }
        }
    }


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        // Destination input field
        userLocation?.let {
            AutocompleteTextView(
                destinationViewModel = destinationViewModel,
                userLocation = it
            )
        }

        // Main screen bottom bar
        if (destinationViewModel.isSheetAvailable) {
            MainScreenBottomBar(
                destinationViewModel = destinationViewModel,
                onRideButtonClicked = onRideButtonClicked,
                rideRepoImpl = rideRepoImpl

            )
        }

        // Google Map view
        if (isLocationInitialized && destinationViewModel.encodedPolyline == null) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude)
        } else if (isLocationInitialized && destinationViewModel.encodedPolyline != null) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude, encodedPolyline = destinationViewModel.encodedPolyline)
        } else {
            Text("Fetching location...")
        }
    }

}

@Composable
@Preview
fun SurgeMainScreenPreview() {
    val context = LocalContext.current // Get the current Context using LocalContext
    val locationViewModel: LocationViewModel = viewModel()
    SurgeMainScreen(
        locationViewModel = locationViewModel,
        onRideButtonClicked = {}
    )
}
