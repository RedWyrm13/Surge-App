package com.example.surge_app.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.surge_app.R

// Screen for users who opted to log in with google. This screen is useless as we havent added the google sign in logic yet.
@Composable
fun GoogleAccountSignUpScreen(
    onGoogleSignUpButtonClicked: () -> Unit,
    onCancelButtonClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = onGoogleSignUpButtonClicked) {
                Text(text = stringResource(R.string.sign_up_using_google))
            }
            Button(onClick = onCancelButtonClicked) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    }
}
