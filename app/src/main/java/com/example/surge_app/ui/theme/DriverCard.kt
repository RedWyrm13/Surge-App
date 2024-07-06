package com.example.surge_app.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.data.Driver
import com.example.surge_app.data.Ride
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import com.example.surge_app.R
import com.example.surge_app.data.minutesHours

@Composable
fun DriverCard(driver: Driver, ride: Ride) {


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

    }
}


@Preview
@Composable
fun DriverCardPreview() {
    DriverCard(driver = Driver(), ride = Ride())
}