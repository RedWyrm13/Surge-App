package com.example.surge_app.ui.theme.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.surge_app.R
import com.example.surge_app.viewModel.LoginCreateAccountViewModel


@Composable
//Function that creates the users profile and account. We are currently using firebase
// to handle user account creation and authentication. This will be upgraded as we scale
// Firebase has built in password requirements. I forget what they are, but it will
// automatically display an error message if the requirements are not met
 fun SignUpScreen(
    loginCreateAccountViewModel: LoginCreateAccountViewModel,
    onSignUpButtonClicked: (String, String) -> Unit,
    onCancelButtonClicked: () -> Unit)
{

    Column(horizontalAlignment = Alignment.CenterHorizontally, //center column horizontally
        verticalArrangement = Arrangement.Center, //center column vertically
        modifier = Modifier.fillMaxSize()) //ensure column takes up full screen
    {
        TextField(
            //Gets user's first name
            value = loginCreateAccountViewModel.firstName,
            onValueChange = { loginCreateAccountViewModel.firstName = it },
            label = {
                Text(
                    stringResource(R.string.first_name),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            //Gets user's last name
            value = loginCreateAccountViewModel.lastName,
            onValueChange = { loginCreateAccountViewModel.lastName = it },
            label = {
                Text(
                    stringResource(R.string.last_name),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            //Gets user's email
            value = loginCreateAccountViewModel.email,
            onValueChange = { loginCreateAccountViewModel.email = it },
            label = {
                Text(
                    stringResource(R.string.email_address),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            //User creates a password
            value = loginCreateAccountViewModel.password,
            onValueChange = {loginCreateAccountViewModel.password = it},
            label = {Text(
                stringResource(R.string.password),
                style = LocalTextStyle.current.copy(fontSize = 8.sp))}
        )
        TextField(
            //User confirms a password
            value = loginCreateAccountViewModel.confirmPassword,
            onValueChange = {loginCreateAccountViewModel.confirmPassword = it},
            label = {Text(
                stringResource(R.string.re_enter_password),
                style = LocalTextStyle.current.copy(fontSize = 8. sp))
            }
        )
        //Check to see if passwords match
        if (!loginCreateAccountViewModel.isCreateAccountValid())
        {
            Text(stringResource(R.string.invalid_signup_information),
                color = Color.Red)
        }

        Row{
            //Buttons to finish account creation or back out
            Button(onClick = onCancelButtonClicked ) {Text(stringResource(R.string.cancel))}
            Button(onClick = {if (loginCreateAccountViewModel.isCreateAccountValid()){
                onSignUpButtonClicked(loginCreateAccountViewModel.email,
                    loginCreateAccountViewModel.password)
            }
                             },
                enabled = loginCreateAccountViewModel.isCreateAccountValid()) // set to isFormValid
            {
                Text(stringResource(R.string.create_account))
            }
        }

    } // end of column of text fields

}

@Composable
@Preview
fun SignUpScreenPreview()
{
    val viewModel = LoginCreateAccountViewModel()
    SignUpScreen(
        loginCreateAccountViewModel = viewModel,
        onCancelButtonClicked = {},
        onSignUpButtonClicked = {_,_ -> }
    )
}


