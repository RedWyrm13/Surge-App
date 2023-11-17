package com.example.surge_app.ui.theme

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.surge_app.R


@Composable
 fun SignUpScreen(
    onSignUpButtonClicked: (String, String) -> Unit,
    onCancelButtonClicked: () -> Unit)
{
    var email by remember {mutableStateOf("")}
    var firstName by remember{mutableStateOf("")}
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember {mutableStateOf("")}
    val isFormValid = email.isNotBlank() && password.isNotBlank() &&
            password == confirmPassword
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize())
    {
        TextField(
            value = firstName,
            onValueChange = { firstName = it },
            label = {
                Text(
                    stringResource(R.string.first_name),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = {
                Text(
                    stringResource(R.string.last_name),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            value = email,
            onValueChange = { email = it },
            label = {
                Text(
                    stringResource(R.string.email_address),
                    style = LocalTextStyle.current.copy(fontSize = 8.sp)
                )
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        TextField(
            value = password,
            onValueChange = {password = it},
            label = {Text(
                stringResource(R.string.password),
                style = LocalTextStyle.current.copy(fontSize = 8.sp))}
        )
        TextField(value = confirmPassword,
            onValueChange = {confirmPassword = it},
            label = {Text(
                stringResource(R.string.re_enter_password),
                style = LocalTextStyle.current.copy(fontSize = 8. sp))
            }
        )
        if (!checkPassword(password = password, confirmPassword = confirmPassword))
        {
            Text(stringResource(R.string.passwords_do_not_match),
                color = Color.Red)
        }

        Row{
            Button(onClick = onCancelButtonClicked ) {Text(stringResource(R.string.cancel))}
            Button(onClick = {if (isFormValid){
                onSignUpButtonClicked(email, password)
            }
                             },
                enabled = isFormValid) // set to isFormValid
            {
                Text(stringResource(R.string.create_account))
            }
        }

    } // end of column of text fields

}

fun checkPassword(password: String, confirmPassword: String): Boolean {
    return(password == confirmPassword)
}





@Composable
@Preview
fun SignUpScreenPreview()
{
    SignUpScreen(
        onCancelButtonClicked = {},
        onSignUpButtonClicked = {_,_ -> }
    )
}


