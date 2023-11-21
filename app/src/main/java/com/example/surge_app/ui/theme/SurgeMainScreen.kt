package com.example.surge_app.ui.theme

import android.location.Location
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.GoogleMapComposable
import com.example.surge_app.LocationComponent


@Composable
fun SurgeMainScreen() {
    var address by remember { mutableStateOf("") }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    )
    {
        TextField(value = address,
            onValueChange = { address = it },
            label = {
                if (address == "") {
                    Text(text = "Where do you want to go?")
                } else {
                    Text(text = "")

                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        var location by remember {
            mutableStateOf(Location("").apply {
                latitude = 36.08   // Latitude for Harry Reid International Airport
                longitude = -115.15 // Longitude for Harry Reid International Airport
            })
        }
        LocationComponent { newLocation ->
            location = newLocation
        }
            GoogleMapComposable(lat = location.latitude, lon = location.longitude)

    }
}

@Composable
@Preview
fun SurgeMainScreenPreview()
{
    SurgeMainScreen()
}
