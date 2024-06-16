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
        val context = LocalContext.current
        // Use remember to retain state across recompositions and mutableStateOf to make it observable
        val text = remember { mutableStateOf("Loading...") }

        val displayDriver = FirebaseManager.getDriverFirestore(context)
        val displayPassenger = FirebaseManager.getPassengerFirestore(context)



        // Fetch the data once. To avoid fetching data on every recomposition, use LaunchedEffect or similar approach
        LaunchedEffect(key1 = Unit) {
            Log.d("AuthDebug", "User logged in: ${FirebaseAuth.getInstance().currentUser != null}")
            displayDriver.collection("Drivers").document("Stark Tony").get().addOnSuccessListener { document ->
                if (document.exists()) {
                    text.value = document.data.toString()  // Update state here
                    Log.d("FirebaseManager", text.value)
                } else {
                    text.value = "Driver not found"
                }
            }.addOnFailureListener {
                text.value = "Error fetching driver"
            }
        }

        // Text will recompose when text.value changes
        Text(text = text.value)

    }
}