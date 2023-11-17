package com.example.surge_app.ui.theme

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun SurgeMainScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Blue
    ) {
        Text(
            "SUCCESS",
            fontSize = 80.sp,
            color = Color.Green
        )
    }
}

@Composable
@Preview
fun SurgeMainScreenPreview()
{
    SurgeMainScreen()
}