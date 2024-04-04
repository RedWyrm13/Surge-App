package com.example.surge_app.ui.theme

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
import com.example.surge_app.data.metersToMiles
import com.example.surge_app.data.secondsToHoursMinutes
import com.example.surge_app.viewModel.DestinationViewModel

//This function is used to create the bottom sheet after the user has entered their destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenBottomBar(destinationViewModel: DestinationViewModel,
                        onRideButtonClicked: () -> Unit) {
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
            ChipInsideBottomBar(destinationViewModel, onRideButtonClicked)
            Text(text = "Fill with images of place you want to go to", modifier = Modifier.align(Alignment.CenterHorizontally))

        }
    }
}

//This chip is in the bottom sheet
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChipInsideBottomBar(
    destinationViewModel: DestinationViewModel,
    onRideButtonClicked: () -> Unit
) {

    Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly) {
        InputChip(
            label = { Text(text = stringResource(R.string.find_ride)) },
            selected = true,
            onClick = {onRideButtonClicked()}
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