package com.example.surge_app.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.DriverCard
import com.example.surge_app.ui.theme.viewModel.RideViewModel


@Composable
fun StartRideScreen(rideRepoImpl: RideRepoImpl, rideViewModel: RideViewModel) {
    val potentialDrivers = rideViewModel.potentialDrivers
    val done = rideViewModel.done

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
        ) {
            // Display "Searching For Drivers" or "Drivers Near You" based on potentialDrivers
            if (potentialDrivers.isEmpty()) {
                Text(text = "Searching For Drivers")
                Text(text = "Done: $done")
            } else {
                Text(text = "Drivers Near You")
                Text(text = "Done: $done")

                // Render list of drivers if available
                for (driver in potentialDrivers) {
                    DriverCard(driver)
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