package com.example.surge_app.ui.theme

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.viewModel.LocationViewModel

@Composable
fun SurgeMainScreen(locationViewModel: LocationViewModel = viewModel()) {
    var address by remember { mutableStateOf("") }

    val location by locationViewModel.userLocation.observeAsState()

    // Default or initial location - change to a more suitable default as needed
    val defaultLocation = Location("").apply {
        latitude = 36.114647  // Example: Las Vegas Latitude
        longitude = -115.172813  // Example: Las Vegas Longitude
    }

    val isLocationInitialized = location != null && location != defaultLocation

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        TextField(
            value = address,
            onValueChange = { address = it },
            label = { Text(text = if (address.isEmpty()) "Where do you want to go?" else "") },
            modifier = Modifier.fillMaxWidth()
        )

        if (isLocationInitialized) {
            // Only show the map when a valid location is available
            GoogleMapComposable(lat = location!!.latitude, lon = location!!.longitude)
        } else {
            // Show loading or placeholder view
            Text("Fetching location...")
        }
    }
}




@Composable
@Preview
fun SurgeMainScreenPreview()
{
    SurgeMainScreen()
}