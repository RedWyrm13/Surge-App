package com.example.surge_app.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.surge_app.R

@Composable
//Renders the login page that takes in the users email and password then navigates to the
// main page. Alternatively users can navigate to the signup page if they realize they do
// not have an account.
fun LoginScreen(
    onSignUpButtonClicked: () -> Unit,
    onLoginButtonClicked: (String, String) -> Unit
){
    //Creates state variables for each of the text fields
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val isFormValid = email!= "" && password != ""
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, //aligns column horizontally
        verticalArrangement = Arrangement.Center, //aligns column vertically
        modifier = Modifier.fillMaxSize() // ensures column takes up full size of screen
    ){
        //User will use this text field to enter their email address
        TextField(
        value = email,
        onValueChange = { email = it },
        label = {
            Text(
                stringResource(R.string.email_address),
                style = LocalTextStyle.current.copy(fontSize = 8.sp)
            )
        })

        // User will use this textfield to enter their password
        //This needs to be updated to be more secure. I do not know how to do that yet
        TextField(
            value = password,
            onValueChange = {password = it},
            label = {
                Text(stringResource(R.string.password),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp))
            }
        )
        Row{
            //Buttons to navigate to the appropriate screen
            Button(onClick = onSignUpButtonClicked){Text(stringResource(R.string.sign_up))}

            //isFormValid is a simple temporary check to make sure
            // password and email are not empty
            Button(onClick = {if (isFormValid){
                onLoginButtonClicked(email, password)} },
                enabled = isFormValid)
            {Text(stringResource(R.string.log_in))}
        }
    } // End of column
}

@Preview
@Composable
fun LoginScreenPreview(){
    LoginScreen(
        onLoginButtonClicked = {_,_ ->},
        onSignUpButtonClicked = {})
}