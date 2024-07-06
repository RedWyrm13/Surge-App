package com.example.surge_app.ui.theme.screens


import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.network.FirebaseManager
import com.example.surge_app.ui.theme.viewModel.DestinationViewModel
import com.example.surge_app.ui.theme.viewModel.LocationViewModel
import com.example.surge_app.ui.theme.viewModel.RideViewModel
import com.google.firebase.auth.FirebaseAuth


@Composable
fun StartRideScreen(rideRepoImpl: RideRepoImpl) {
    val rideViewModel = RideViewModel(rideRepoImpl)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center) {
            Text(text = "SUCCESS FOR NOW!")

        }
    }
}