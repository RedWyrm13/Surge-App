package com.example.surge_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.surge_app.ui.theme.SurgeAppTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val authManager = AuthenticationManager(this)

        setContent {
            SurgeAppTheme {
                SurgeApp(onSignUpButtonClicked = { email, password ->
                    authManager.signUpUser(email, password)
                }
                )
            }
        }
    }
}