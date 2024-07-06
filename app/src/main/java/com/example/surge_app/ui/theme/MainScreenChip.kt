package com.example.surge_app.ui.theme

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.InputChip
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.surge_app.R
import com.example.surge_app.data.Ride
import com.example.surge_app.data.SimpleLocation
import com.example.surge_app.data.apiResponseData.metersToMiles
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.data.apiResponseData.secondsToHoursMinutes
import com.example.surge_app.ui.theme.viewModel.DestinationViewModel
import com.example.surge_app.ui.theme.viewModel.LocationViewModel
import com.example.surge_app.ui.theme.viewModel.RideViewModel

//This function is used to create the bottom sheet after the user has entered their destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBottomBar(destinationViewModel: DestinationViewModel,
                        onRideButtonClicked: () -> Unit,
                        rideRepoImpl: RideRepoImpl,
                        locationViewModel: LocationViewModel,
                        rideViewModel: RideViewModel
) {
    ModalBottomSheet(
        onDismissRequest = { destinationViewModel.isSheetAvailable = false },
        modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(text = distanceAndTimeText(destinationViewModel.distanceOfRoute, destinationViewModel.durationOfRoute),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = Color.Green)
            ChipInsideBottomBar(destinationViewModel, onRideButtonClicked, rideRepoImpl, locationViewModel, rideViewModel)
            Text(text = "Fill with images of place you want to go to", modifier = Modifier.align(Alignment.CenterHorizontally))

        }
    }
}

//This chip is in the bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipInsideBottomBar(
    destinationViewModel: DestinationViewModel,
    onRideButtonClicked: () -> Unit,
    rideRepoImpl: RideRepoImpl,
    locationViewModel: LocationViewModel,
    rideViewModel: RideViewModel
) {
     rideViewModel.pickupLocation = SimpleLocation(locationViewModel.getLatestLatitude(), locationViewModel.getLatestLongitude())

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        InputChip(
            label = { Text(text = stringResource(R.string.find_ride)) },
            selected = true,
            onClick = {
                onRideButtonClicked()
                rideViewModel.addRideToDatabase(createRide(destinationViewModel, locationViewModel))
                rideViewModel.fetchDriversInArea()
            }
        )
        InputChip(
            label = { Text(text = stringResource(R.string.cancel)) },
            selected = false,
            onClick = {
                destinationViewModel.isSheetAvailable = false
            }
        )
    }
}

//This function formats the distance and time to display in the chip on the bottom sheet

fun distanceAndTimeText(distance: Int, time: String): String {
    val timeInHoursMinutes = secondsToHoursMinutes(time.filter { it.isDigit() }.toInt())
    val distanceInMiles = metersToMiles(distance)
    return "$distanceInMiles miles, $timeInHoursMinutes"
}

fun createRide(destinationViewModel: DestinationViewModel,
               locationViewModel: LocationViewModel
): Ride {
    val pickupLocation = SimpleLocation(locationViewModel.getLatestLatitude(), locationViewModel.getLatestLongitude())
    try {
        val ride = Ride(duration = destinationViewModel.durationOfRoute,
            distance = destinationViewModel.distanceOfRoute,
            encodedPolyline = destinationViewModel.encodedPolyline,
            pickupLocation = pickupLocation,
            pickupLocationAddress = locationViewModel.reverseGeocodeAddress(),
            destinationLocationAddress = destinationViewModel.predictions.value.predictions[0].description,
            destinationLocation = destinationViewModel.destinationLocation!!)
        return ride

    }
    catch (e: Exception) {
        Log.e("MyTag_MainScreenChip", "createRide: $e")
        return Ride()
    }




}