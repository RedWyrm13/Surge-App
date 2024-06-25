package com.example.surge_app.ui.theme.screens

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.AutocompleteTextView
import com.example.surge_app.ui.theme.MainScreenBottomBar
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel

//Everything has stopped working :/
@Composable
fun SurgeMainScreen(
    locationViewModel: LocationViewModel,
    destinationViewModel: DestinationViewModel = viewModel(),
    onRideButtonClicked: () -> Unit,
    rideRepoImpl: RideRepoImpl
) {
    // State variables
    val destination by remember { mutableStateOf("") }

    var geocodedLocation by remember { mutableStateOf<Location?>(null) }

    Log.d("SurgeMainScreen", "Right Before User Location definiton")
    val userLocation by locationViewModel.userLocation.observeAsState()
    Log.d("SurgeMainScreen", "User Location is $userLocation")
    // Default location
    val defaultLocation = Location("").apply {
        latitude = 36.114647
        longitude = -115.172813
    }

    // Location initialization check
    val isLocationInitialized = userLocation != null && userLocation != defaultLocation

    //I am not sure why this is here. I commented it out and nothing changed, but I am not going to delete it just yet
    // LaunchedEffect to observe destination changes
//    LaunchedEffect(destination) {
//        if (destination.isNotEmpty()) {
//            locationViewModel.geocodeAddress(destination).observeForever {
//                geocodedLocation = it
//            }
//        }
//    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Google Map view
        if (isLocationInitialized && destinationViewModel.encodedPolyline == null) {
            GoogleMapComposable(
                lat = userLocation!!.latitude,
                lon = userLocation!!.longitude,
                modifier = Modifier.fillMaxSize() // Pass the modifier
            )
        } else if (isLocationInitialized && destinationViewModel.encodedPolyline != null) {
            GoogleMapComposable(
                lat = userLocation!!.latitude,
                lon = userLocation!!.longitude,
                encodedPolyline = destinationViewModel.encodedPolyline,
                modifier = Modifier.fillMaxSize() // Pass the modifier
            )
        } else {
            Text("Fetching location...", modifier = Modifier.align(Alignment.Center))
        }

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth() // Use fillMaxWidth() to only take horizontal space
            ) {
                IconButton(
                    onClick = { /* TODO*/ },
                    modifier = Modifier.scale(1.4f)) {
                    Icon(Icons.Filled.Settings, contentDescription = "Settings")

                }
                // Use Box with weight to make the text field take the remaining space
                Box(modifier = Modifier.weight(1f)) {
                    userLocation?.let {
                        AutocompleteTextView(
                            destinationViewModel = destinationViewModel,
                            userLocation = it
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(.25f))
            }


            // Main screen bottom bar
            if (destinationViewModel.isSheetAvailable) {
                MainScreenBottomBar(
                    destinationViewModel = destinationViewModel,
                    onRideButtonClicked = onRideButtonClicked,
                    rideRepoImpl = rideRepoImpl,
                    locationViewModel = locationViewModel
                )
            }
        }
    }
}


@Composable
@Preview
fun SurgeMainScreenPreview() {
    val locationViewModel: LocationViewModel = LocationViewModel(application = Application())
    SurgeMainScreen(
        locationViewModel = locationViewModel,
        onRideButtonClicked = {},
        destinationViewModel = DestinationViewModel(rideRepoImpl = RideRepoImpl()),
        rideRepoImpl = RideRepoImpl()
    )
}
