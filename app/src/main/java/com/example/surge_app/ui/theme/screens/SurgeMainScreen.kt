package com.example.surge_app.ui.theme.screens

import android.content.Context
import android.location.Location
import android.util.Log
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
import com.example.surge_app.network.FirebaseManager
import com.example.surge_app.ui.theme.AutocompleteTextView
import com.example.surge_app.ui.theme.MainScreenBottomBar
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel

@Composable
fun SurgeMainScreen(
    locationViewModel: LocationViewModel,
    destinationViewModel: DestinationViewModel = viewModel(),
    context: Context,
    onRideButtonClicked: () -> Unit
) {
    Log.d("SurgeMainScreen", "Composable function started")

    // State variables
    val destination by remember { mutableStateOf("") }
    Log.d("SurgeMainScreen", "Destination state initialized")

    var geocodedLocation by remember { mutableStateOf<Location?>(null) }
    Log.d("SurgeMainScreen", "GeocodedLocation state initialized")

    val userLocation by locationViewModel.userLocation.observeAsState()
    Log.d("SurgeMainScreen", "UserLocation state observed")

    val displayDriver = FirebaseManager.getDriverFirestore(context)
    Log.d("SurgeMainScreen", "Driver Firestore initialized")

    // Default location
    val defaultLocation = Location("").apply {
        latitude = 36.114647
        longitude = -115.172813
    }
    Log.d("SurgeMainScreen", "Default location initialized")

    // Location initialization check
    val isLocationInitialized = userLocation != null && userLocation != defaultLocation
    Log.d("SurgeMainScreen", "isLocationInitialized: $isLocationInitialized")

    // LaunchedEffect to observe destination changes
    LaunchedEffect(destination) {
        Log.d("SurgeMainScreen", "LaunchedEffect started for destination")
        if (destination.isNotEmpty()) {
            locationViewModel.geocodeAddress(destination).observeForever {
                geocodedLocation = it
                Log.d("SurgeMainScreen", "Geocoded location updated: $geocodedLocation")
            }
        }
    }

    Log.d("SurgeMainScreen", "Before Column Composable")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Log.d("SurgeMainScreen", "Inside Column Composable")

        // Destination input field
        userLocation?.let {
            AutocompleteTextView(
                destinationViewModel = destinationViewModel,
                userLocation = it
            )
            Log.d("SurgeMainScreen", "AutocompleteTextView called with userLocation: $it")
        }

        // Main screen bottom bar
        if (destinationViewModel.isSheetAvailable) {
            MainScreenBottomBar(
                destinationViewModel = destinationViewModel,
                onRideButtonClicked = onRideButtonClicked,
                displayDriver = displayDriver
            )
            Log.d("SurgeMainScreen", "MainScreenBottomBar called")
        }

        // Google Map view
        if (isLocationInitialized && destinationViewModel.encodedPolyline == null) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude)
            Log.d("SurgeMainScreen", "GoogleMapComposable called with user location")
        } else if (isLocationInitialized && destinationViewModel.encodedPolyline != null) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude, encodedPolyline = destinationViewModel.encodedPolyline)
            Log.d("SurgeMainScreen", "GoogleMapComposable called with polyline")
        } else {
            Text("Fetching location...")
            Log.d("SurgeMainScreen", "Text 'Fetching location...' displayed")
        }
    }

    Log.d("SurgeMainScreen", "Composable function ended")
}

@Composable
@Preview
fun SurgeMainScreenPreview() {
    val context = LocalContext.current // Get the current Context using LocalContext
    Log.d("SurgeMainScreenPreview", "Preview Composable function started")
    val locationViewModel: LocationViewModel = viewModel()
    SurgeMainScreen(
        context = context,
        locationViewModel = locationViewModel,
        onRideButtonClicked = { Log.d("SurgeMainScreenPreview", "Ride button clicked in preview") }
    )
    Log.d("SurgeMainScreenPreview", "Preview Composable function ended")
}
