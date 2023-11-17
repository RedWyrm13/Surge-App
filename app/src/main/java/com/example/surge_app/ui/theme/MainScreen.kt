package com.example.surge_app.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(){
    Text(text = "SUCCESS",
        color = Color.Green,
        fontSize = 100.sp)
}

@Preview
@Composable
fun MainScreenPreview(){
    MainScreen()
}