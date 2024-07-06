package com.example.surge_app.data

import java.text.DecimalFormat
import java.util.Calendar

class Driver(
    val firstName: String = "",
    val lastName: String =  "",
    val license: String = "",
    val phone: String=  "",
    val email: String = "",
    val id: String = "",
    val birthday: Birthday = Birthday(),
    val address: Address = Address(),
    val age: Int = 0,
    val geohash: String = "",
    val rating: DriverRating = DriverRating(),
    val fare: Fare = Fare(),

){
    constructor() : this("","","","","","", Birthday(), Address(), 0, "",DriverRating(),Fare())
}


class Birthday(
    val day: Int = 10,
    val month: Int = 25,
    val year: Int = 2000
)

class Address(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zip: String = "",
)
class DriverRating(
    val rating: Double = 0.0,
    val ratingCount: Int = 0
)

class Fare(
    val pricePerMile: Double = 1.0,
    val pricePerMinute: Double = 1.0
){
    fun calculatePriceOfRide(distance: Int, time: Int): Double {
        //convert distance to miles from meters and time from seconds to minutes
        val price = distance *0.000621371 * pricePerMile + time / 60 * pricePerMinute

        val df = DecimalFormat("#.##")
        return df.format(price).toDouble()
    }
}