package com.example.surge_app.data

import java.util.Calendar

class Driver(val firstName: String = "",
             val lastName: String =  "",
             val license: String = "",
             val phone: String=  "",
             val email: String = "",
             val id: String = "",
             val birthday: Birthday = Birthday(),
             val address: Address = Address(),
             val age: Int = 0,
             val geohash: String = ""
){
    constructor() : this("","","","","","", Birthday(), Address(), 0, "")
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
