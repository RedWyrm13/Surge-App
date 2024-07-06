package com.example.surge_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.R
import com.example.surge_app.data.Driver

@Composable
fun DriverCard(driver: Driver) {


    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        LeftColumn(driver = driver)
        RightColumn(driver = driver)

    }
}
@Composable
fun LeftColumn(driver: Driver) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(painter = painterResource(id = R.drawable.emptyuserphoto) , contentDescription = "Driver Image")
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = driver.firstName)
            Text(text = "Add Driver Rating Here") //Add rating to driver class

        }
        Text(text = "Add short driver Bio." ) //Add bio to driver class
    }

}

@Composable
fun RightColumn(driver: Driver) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Estimated Price" ) // Add rate to driver class
            Text(text = "Time until driver arrives")
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Estimated Pickup Time")
            Text(text = "Estimated Dropoff Time")

        }

    }
}


@Preview
@Composable
fun DriverCardPreview() {
    DriverCard(driver = Driver())
}