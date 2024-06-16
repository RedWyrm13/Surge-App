package com.example.surge_app.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.surge_app.network.FirebaseManager
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel
import com.example.surge_app.viewModel.RideViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun StartRideScreen(rideViewModel: RideViewModel, locationViewModel: LocationViewModel, destinationViewModel: DestinationViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "SUCCESS! YOU HAVE REQUESTED A RIDE!")

    }
}