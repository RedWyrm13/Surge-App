package com.example.surge_app.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.surge_app.R

@Composable
fun LogInOrCreateAccount(onLoginButtonClicked: () -> Unit,
                         onSignUpButtonClicked: () -> Unit)
{Surface(modifier = Modifier.fillMaxSize(),
    color = Color.Blue
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(
            text = stringResource(R.string.surge),
            fontSize = 60.sp,
            color = Color.White
        )
        //Spacer(modifier = Modifier.size(100.dp))
        Image(
            painter = painterResource(id = R.drawable.surge_image),
            contentDescription = null,
            modifier = Modifier
                .size(400.dp, 400.dp)
                .clip(CircleShape)
        )
      //  Spacer(modifier = Modifier.size(20.dp))
        Text(text = stringResource(R.string.Surge_Moto),
            fontSize = 30.sp,
            color = Color.White,
        )
      //  Spacer( modifier = Modifier.size(120.dp))
        Row (
        ){
            Button(
                onClick = onSignUpButtonClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                )
            ) {
                Text(text = stringResource(R.string.sign_up))

            }
      //      Spacer(modifier = Modifier.size(60.dp))

            Button(
                onClick = onLoginButtonClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,  // Set the background color
                    contentColor = Color.White,
                )
            ) {
                Text(text = stringResource(R.string.log_in))
            }
        }
    }
}
}

@Preview(showBackground = true)
@Composable
fun LogInOrCreateAccountScreenPreview() {
    SurgeAppTheme {
        LogInOrCreateAccount(
            onLoginButtonClicked = {},
            onSignUpButtonClicked = {})
    }
}