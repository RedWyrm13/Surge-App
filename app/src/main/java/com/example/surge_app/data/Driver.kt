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
){
    var age = birthday.calculateAge()
}


class Birthday(
    val day: Int = 10,
    val month: Int = 25,
    val year: Int = 2000
) {
    fun calculateAge(): Int {
        val currentDate = Calendar.getInstance()
        val birthDate = Calendar.getInstance().apply {
            set(year, month - 1, day) // month in Calendar starts from 0
        }
        var age = currentDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR)
        if (currentDate.get(Calendar.DAY_OF_YEAR) < birthDate.get(Calendar.DAY_OF_YEAR)) {
            age--
        }
        return age
    }
}

class Address(
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val zip: String = "",
)
