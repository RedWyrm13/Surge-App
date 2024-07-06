package com.example.surge_app.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.surge_app.R
import com.example.surge_app.ui.theme.viewModel.LoginCreateAccountViewModel

@Composable
//Renders the login page that takes in the users email and password then navigates to the
// main page. Alternatively users can navigate to the signup page if they realize they do
// not have an account.
fun LoginScreen(loginCreateAccountViewModel: LoginCreateAccountViewModel,
                onSignUpButtonClicked: () -> Unit,
                onLoginButtonClicked: (String, String) -> Unit
){

    val isFormValid = loginCreateAccountViewModel.email!= "" && loginCreateAccountViewModel.password != ""
    Column(
        horizontalAlignment = Alignment.CenterHorizontally, //aligns column horizontally
        verticalArrangement = Arrangement.Center, //aligns column vertically
        modifier = Modifier.fillMaxSize() // ensures column takes up full size of screen
    ){
        //User will use this text field to enter their email address
        TextField(
        value = loginCreateAccountViewModel.email,
        onValueChange = { loginCreateAccountViewModel.email = it },
        label = {
            Text(
                stringResource(R.string.email_address),
                style = LocalTextStyle.current.copy(fontSize = 8.sp)
            )
        })

        // User will use this textfield to enter their password
        //This needs to be updated to be more secure. I do not know how to do that yet
        TextField(
            value = loginCreateAccountViewModel.password,
            onValueChange = {loginCreateAccountViewModel.password = it},
            label = {
                Text(stringResource(R.string.password),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp))
            },
            visualTransformation = PasswordVisualTransformation(),)
        Row{
            //Buttons to navigate to the appropriate screen
            Button(onClick = onSignUpButtonClicked){Text(stringResource(R.string.sign_up))}

            //isFormValid is a simple temporary check to make sure
            // password and email are not empty
            Button(onClick = {if (isFormValid){
                onLoginButtonClicked(loginCreateAccountViewModel.email, loginCreateAccountViewModel.password)} },
                enabled = isFormValid)
            {Text(stringResource(R.string.log_in))}
        }
    } // End of column
}

@Preview
@Composable
fun LoginScreenPreview() {
    val viewModel = LoginCreateAccountViewModel() // Create a mock ViewModel instance
    LoginScreen(
        loginCreateAccountViewModel = viewModel,
        onLoginButtonClicked = { _, _ -> },
        onSignUpButtonClicked = {}
    )
}