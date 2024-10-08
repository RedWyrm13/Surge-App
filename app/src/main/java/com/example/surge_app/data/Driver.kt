package com.example.surge_app.data

import java.text.DecimalFormat
import java.util.Random

class Driver(
    val firstName: String = "",
    val rating: DriverRating = DriverRating(),
    val fare: Fare = Fare(),
    val driverIdNumber: String = ""

){
    constructor() : this("", DriverRating(), Fare(), "")

    override fun toString(): String {
        return "Driver(firstName='$firstName', rating=$rating, fare=$fare, driverIdNumber='$driverIdNumber')"}
}

class DriverRating(
    val rating: Double = 0.0,
    val ratingCount: Int = 0
)

class Fare(
     val pricePerMile: Double = 1.0,
     val pricePerMinute: Double = 1.0
){
    fun calculatePriceOfRide(distance: Int, time: Int): String {
        //convert distance to miles from meters and time from seconds to minutes
        val price = distance *0.000621371 * pricePerMile + time / 60 * pricePerMinute

        val df = DecimalFormat("0.00")
        return df.format(price).toString()
    }
}
fun generateUniqueId(method: String): String {
    val chars = "ABCEFGHIJKLMNOQSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
    val random = Random()
    val sb = StringBuilder(15)
    for (i in 0 until 15) {
        sb.append(chars[random.nextInt(chars.length)])
    }
    when (method.lowercase().trim()) {
        "ride" -> {
            return "R_$sb"
        }
        "pax" -> {
            return "P_$sb"
        }
        "driver" -> {
            return "D_$sb"
        }
    }
    return sb.toString()
}