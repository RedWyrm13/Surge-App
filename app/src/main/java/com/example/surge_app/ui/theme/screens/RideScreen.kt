package com.example.surge_app.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.DriverCard
import com.example.surge_app.ui.theme.viewModel.RideViewModel


@Composable
fun StartRideScreen(rideRepoImpl: RideRepoImpl, rideViewModel: RideViewModel) {
    val potentialDrivers = rideViewModel.potentialDrivers.collectAsState().value
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Top,
            modifier = Modifier.verticalScroll(scrollState))
        {
            // Display "Searching For Drivers" or "Drivers Near You" based on potentialDrivers
            if (potentialDrivers.isEmpty()) {
                Text(text = "Searching For Drivers", fontSize = 24.sp)
            } else {
                Text(
                    text = "Pickup Location: ${rideViewModel.ride!!.pickupLocationAddress}",
                    modifier = Modifier.padding(8.dp)
                )
                Text(
                    text = "Dropoff Location: ${rideViewModel.ride!!.destinationLocationAddress}",
                    modifier = Modifier.padding(8.dp))

                Text(text = "Drivers Near You", fontSize = 24.sp)

                // Render list of drivers if available
                for (driver in potentialDrivers) {
                    Spacer(modifier = Modifier.height(10.dp))
                    DriverCard(driver, rideViewModel.ride!!)
                }
            }
        }
    }

    // Trigger fetching drivers when the screen appears or based on user interaction
    LaunchedEffect(Unit) {
        rideViewModel.fetchDriversInArea()
    }
}


@Preview
@Composable
fun PreviewStartRideScreen() {
    StartRideScreen(RideRepoImpl(), RideViewModel(RideRepoImpl()))
}