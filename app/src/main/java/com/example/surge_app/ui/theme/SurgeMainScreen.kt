package com.example.surge_app.ui.theme

import android.content.Context
import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.viewModel.LocationViewModel
import com.google.android.libraries.places.api.Places
import java.util.Properties

@Composable
//This is the main screen of the app. It creates a google map view of where the user is at
// and it will allow the user to type in a destination for a Surge ride
fun SurgeMainScreen(locationViewModel: LocationViewModel = viewModel()) {

    //State variables
    var destination by remember { mutableStateOf("") }
    var geocodedLocation by remember { mutableStateOf<Location?>(null) }
    val userLocation by locationViewModel.userLocation.observeAsState()

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
    //I dont really remember what this does. It will probably get changed later. I think I stole
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
        //This is displayed at the top of the page for the user to input a destination. It doesn't do
        //anything yet but the destination will be used to create the ride
        TextField(
            value = destination,
            onValueChange = { destination = it },
            label = { Text(text = if (destination.isEmpty()) "Where do you want to go?" else "") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { /* Handle onDone if needed */ })
        )

        //This draws the google map viewing of the user's current location.
        if (isLocationInitialized) {
            GoogleMapComposable(lat = userLocation!!.latitude, lon = userLocation!!.longitude)
        } else {
            //If the map is not ready to view, this text is displayed in place of the map
            Text("Fetching location...")
        }

        //I do not know what this is
        geocodedLocation?.let {
            Text("Geocoded Location: Lat: ${it.latitude}, Lon: ${it.longitude}")
        }
    }
}



@Composable
@Preview
fun SurgeMainScreenPreview()
{
    SurgeMainScreen()
}