package com.example.surge_app.data


data class LocationRestriction(
    val circle: Circle
)

data class Circle(
    val center: Center,
    val radius: Double
)

data class Center(
    val latitude: Double,
    val longitude: Double
)
