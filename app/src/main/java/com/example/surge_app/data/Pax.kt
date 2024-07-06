package com.example.surge_app.data

//This is the class to hold the information for the user on the passenger app
data class Pax(
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val email: String = "",
    val password: String = "",
    val phoneNumber: String = "",
    val paxId: String = generateUniqueId("pax")

)