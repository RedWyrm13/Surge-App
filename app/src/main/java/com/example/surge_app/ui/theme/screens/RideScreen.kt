package com.example.surge_app.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.surge_app.network.FirebaseManager
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel
import com.example.surge_app.viewModel.RideViewModel


@Composable
fun StartRideScreen(rideViewModel: RideViewModel, locationViewModel: LocationViewModel, destinationViewModel: DestinationViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val result by FirebaseManager.getDriverTest("Maciejunes Andrew").collectAsState(initial = Result.failure(RuntimeException("Loading...")))

       /* when (val unwrappedResult = result.getOrNull()) {
            null -> Text("Error: ${result.exceptionOrNull()?.message ?: "Unknown error"}")
            else -> Text("Driver Data: $unwrappedResult")
        }*/
        Text(text = rideViewModel.encodedPolyline.toString() + "\n" + rideViewModel.distanceOfRoute.toString() + "\n" + rideViewModel.durationOfRoute.toString())
        Text(text = destinationViewModel.encodedPolyline.toString() + "\n" + destinationViewModel.distanceOfRoute.toString() + "\n" + destinationViewModel.durationOfRoute.toString())

        Log.d("RouteInfo for rideViewModel", "${rideViewModel.encodedPolyline}\n${rideViewModel.distanceOfRoute}\n${rideViewModel.durationOfRoute}")
        Log.d("RouteInfo for destination", "${destinationViewModel.encodedPolyline}\n${destinationViewModel.distanceOfRoute}\n${destinationViewModel.durationOfRoute}")

    }
}