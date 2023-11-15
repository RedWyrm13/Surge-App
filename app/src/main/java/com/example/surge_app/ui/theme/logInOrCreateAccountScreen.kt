package com.example.surge_app.ui.theme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.R

@Composable
fun LogInOrCreateAccount(){
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center    ) {
        Button(onClick = {/* TODO */}){
            Text(text = stringResource(R.string.log_in))
        }
        Button(onClick = {/* TODO */}){
            Text(text = stringResource(R.string.sign_up))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SurgeAppTheme {
        LogInOrCreateAccount()
    }
}