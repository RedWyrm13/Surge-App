package com.example.surge_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.surge_app.ui.theme.SurgeAppTheme
import com.google.firebase.FirebaseApp

class LoginCreateAccountActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        val authManager = AuthenticationManager(this, this)

        setContent {
            SurgeAppTheme {
                SurgeApp(onSignUpButtonClicked = { email, password ->
                    authManager.signUpUser(email, password,
                        nextActivityClass = MainScreenActivity::class.java)},
                    onLoginButtonClicked = {email,password ->
                        authManager.loginUser(email,password, MainScreenActivity::class.java)}
                )
            }
        }
    }
}
class MainScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        PlacesApiManager.initializePlaces(applicationContext)
        setTheme(R.style.Theme_SurgeApp)
        super.onCreate(savedInstanceState)

        setContent {
            val context = this
            SurgeAppTheme {
                SurgeMain(context = context)
                CheckAndRequestPermission(
                    onPermissionGranted = {
                        // Handle permission granted
                    },
                    onPermissionDenied = {
                        // Handle permission denied
                    }
                )
            }
        }
    }
}