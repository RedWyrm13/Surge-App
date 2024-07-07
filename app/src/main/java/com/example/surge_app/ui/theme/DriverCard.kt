package com.example.surge_app.ui.theme

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.surge_app.R
import com.example.surge_app.data.Driver
import com.example.surge_app.data.minutesHours
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.viewModel.RideViewModel

@Composable
fun DriverCard(driver: Driver, rideViewModel: RideViewModel) {
    val ride = rideViewModel.ride!!

    val onClick: () -> Unit = {
        Log.d("MyTag_DriverCard", "DriverBefore: $driver")
        ride.requestedDrivers.add(driver.driverIdNumber)
        rideViewModel.addRideToDatabase()

    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )

    {
        Text(text = driver.firstName, fontSize = 20.sp)

        Image(painter = painterResource(id = R.drawable.emptyuserphoto), contentDescription = "User Photo")

        Text("Ride Price: $${driver.fare.calculatePriceOfRide(ride.distance, ride.duration)}")

        Text("Estimated Pickup: 5 minutes") //Add function to calculate pickup time

        Text("Dropoff Time: ${ride.duration.minutesHours()}")

        Row {
            Text("Stars") //Draw stars equal to drivers rating
            Text("(${driver.rating.ratingCount})")
        }

        RequestRideChip(onClick)

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestRideChip(onClick: () -> Unit = {}) {
    var label by remember { mutableStateOf("Request Ride") }
    var selected by remember { mutableStateOf(true) }

    InputChip(
        label = { Text(label) },
        selected = selected,
        onClick = {
            onClick()
            label = "Request Sent"
            selected = false
        }
    )
}
@Preview
@Composable
fun DriverCardPreview() {
    DriverCard(driver = Driver(), rideViewModel = RideViewModel(rideRepoImpl = RideRepoImpl()))
}