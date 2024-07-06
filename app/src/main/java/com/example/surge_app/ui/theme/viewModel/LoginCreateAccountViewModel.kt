package com.example.surge_app.ui.theme.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

// This viewmodel holds all the information for account creation

class LoginCreateAccountViewModel: ViewModel() {
    var email: String by mutableStateOf("")
    var password: String by mutableStateOf("")
    var confirmPassword: String by mutableStateOf("")
    var firstName: String by mutableStateOf("")
    var lastName: String by mutableStateOf("")

    //Returns a value of true if the account creation requirements are met. This requirements will be
    //updated to be more specific.
     fun isCreateAccountValid(): Boolean{
        return email.isNotBlank() && password.isNotBlank() && password ==confirmPassword
    }
    /*
    Returns a value of true if the login information is not blank. This will be updated to
    check the validity of the login information
     */
     fun isLoginValid(): Boolean{
        return email.isNotBlank() && password.isNotBlank()
    }

}