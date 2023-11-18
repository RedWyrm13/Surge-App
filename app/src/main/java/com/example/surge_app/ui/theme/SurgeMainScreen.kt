package com.example.surge_app.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.surge_app.GoogleMapComposable

@Composable
fun SurgeMainScreen() {
    GoogleMapComposable()
}

@Composable
@Preview
fun SurgeMainScreenPreview()
{
    SurgeMainScreen()
}
