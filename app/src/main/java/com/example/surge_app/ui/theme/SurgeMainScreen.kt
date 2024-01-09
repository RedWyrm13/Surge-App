package com.example.surge_app.ui.theme

import android.content.Context
import android.location.Location
import android.util.Log
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.R
import com.example.surge_app.viewModel.LocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL
import java.util.Properties

@Composable
//This is the main screen of the app. It creates a google map view of where the user is at
// and it will allow the user to type in a destination for a Surge ride
fun SurgeMainScreen(locationViewModel: LocationViewModel = viewModel(),
                    context: Context) {


    //State variables
    val destination by remember { mutableStateOf("") }
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
        //Function and its description are in this file, scroll down.
        CreatePlacesTextField(context = context)

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
//This function renders the text field to prompt users for destination inputs. It will return long/lat coordinates
//so that a driving path may be established
fun CreatePlacesTextField(context: Context) {
    var destination by remember { mutableStateOf("") }
    var latitude by remember { mutableDoubleStateOf(0.0) }
    var longitude by remember { mutableDoubleStateOf(0.0) }

    val scope = rememberCoroutineScope()

    // Function to fetch latitude and longitude from Google Places API
    fun fetchCoordinates(context: Context) {
        val properties = Properties()
        val inputStream = context.assets.open("secrets.properties")
        properties.load(inputStream)
        val apiKey = properties.getProperty("apiKey")
        val url = "https://maps.googleapis.com/maps/api/geocode/json?address=$destination&key=$apiKey"



        scope.launch {
            try {
                val response = withContext(Dispatchers.IO) {
                    URL(url).readText()
                }

                val jsonObject = JSONObject(response)
                val results = jsonObject.getJSONArray("results")
                if (results.length() > 0) {
                    val location = results.getJSONObject(0).getJSONObject("geometry").getJSONObject("location")
                    latitude = location.getDouble("lat")
                    longitude = location.getDouble("lng")
                    Log.d("MyTagLatitude", "$latitude")
                    Log.d("MyTagLongtitude", "$longitude")
                }
            } catch (e: Exception) {
                //


            }
        }
    }

    TextField(modifier = Modifier.fillMaxWidth(),
        value = destination,
        onValueChange = {
            destination = it
            latitude = 0.0 // Reset the coordinates when the destination changes
            longitude = 0.0
        },
        label = { Text(text = stringResource(R.string.where_do_you_want_to_go))},
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {

            fetchCoordinates(context) // Fetch coordinates when the user presses "Done"
        }),
        textStyle = TextStyle(color = Color.Black)
    )

    if (latitude != 0.0 && longitude != 0.0) {
        // Display the latitude and longitude if available
        Text("Latitude: $latitude, Longitude: $longitude")
    }
}



@Composable
@Preview
fun SurgeMainScreenPreview() {
    val context = LocalContext.current // Get the current Context using LocalContext
    SurgeMainScreen(context = context)
}