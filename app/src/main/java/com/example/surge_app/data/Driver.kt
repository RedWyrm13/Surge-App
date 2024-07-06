package com.example.surge_app.data

import java.text.DecimalFormat

class Driver(
    val firstName: String = "",
    val rating: DriverRating = DriverRating(),
    val fare: Fare = Fare(),

){
    constructor() : this("", DriverRating(), Fare())
}

class DriverRating(
    val rating: Double = 0.0,
    val ratingCount: Int = 0
)

class Fare(
    private val pricePerMile: Double = 1.0,
    private val pricePerMinute: Double = 1.0
){
    fun calculatePriceOfRide(distance: Int, time: Int): Double {
        //convert distance to miles from meters and time from seconds to minutes
        val price = distance *0.000621371 * pricePerMile + time / 60 * pricePerMinute

        val df = DecimalFormat("#.##")
        return df.format(price).toDouble()
    }
}