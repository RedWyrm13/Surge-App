package com.example.surge_app.ui.theme.screens

import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.ui.theme.AutocompleteTextView
import com.example.surge_app.ui.theme.MainScreenBottomBar
import com.example.surge_app.viewModel.DestinationViewModel
import com.example.surge_app.viewModel.LocationViewModel

@Composable
fun SurgeMainScreen(
    locationViewModel: LocationViewModel = viewModel(),
    context: Context,
    onRideButtonClicked: () -> Unit
) {
    //State variables
    val destination by remember { mutableStateOf("") }
    var geocodedLocation by remember { mutableStateOf<Location?>(null) }
    val userLocation by locationViewModel.userLocation.observeAsState()
    val destinationViewModel: DestinationViewModel = viewModel()


    //If we cannot retrieve the user's location it will display Las Vegas on the google map view
    // because Vegas is where I had the idea for the app. This can be changed to something more
    //practical such as an error message or last known location view.
    val defaultLocation = Location("").apply {
        latitude = 36.114647
        longitude = -115.172813
    }

    //Creates a boolean value of true if the user's location is a non null location other than
    //the default location
    val isLocationInitialized = userLocation != null && userLocation != defaultLocation


    //LaunchedEffect to observe destination changes
    //I don't really remember what this does. It will probably get changed later. I think I stole
    //this from ChatGPT
    LaunchedEffect(destination) {
        if (destination.isNotEmpty()) {
            locationViewModel.geocodeAddress(destination).observeForever {
                geocodedLocation = it
            }
        }
    }


    Column(
        //It is just a column
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        //If the value of userLocation is not null, it calls the text field composable that takes in user input for their destination
        userLocation?.let {
            AutocompleteTextView(
                destinationViewModel = destinationViewModel,
                userLocation = it
            )
        }
        if (destinationViewModel.isSheetAvailable) {
            MainScreenBottomBar(destinationViewModel = destinationViewModel,
                onRideButtonClicked = onRideButtonClicked)
        }


                //This draws the google map viewing of the user's current location.
        if (isLocationInitialized && destinationViewModel.encodedPolyline == null) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude)
        }
        else if(isLocationInitialized && destinationViewModel.encodedPolyline != null){
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude, encodedPolyline = destinationViewModel.encodedPolyline)
        }
        else {
            //If the map is not ready to view, this text is displayed in place of the map
            Text("Fetching location...")
        }


    }
}


@Composable
@Preview
fun SurgeMainScreenPreview() {
    val context = LocalContext.current // Get the current Context using LocalContext
    SurgeMainScreen(context = context,
        onRideButtonClicked = {  })
}

