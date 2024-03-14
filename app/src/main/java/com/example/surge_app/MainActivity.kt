package com.example.surge_app

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.surge_app.network.PlacesApiManager
import com.example.surge_app.ui.theme.SurgeAppTheme
import com.google.firebase.FirebaseApp

class LoginCreateAccountActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        // Firebase account manager stuff
        val authManager = AuthenticationManager(this, this)

        setContent {
            SurgeAppTheme {
                SurgeApp(
                    onSignUpButtonClicked = { email, password ->
                        authManager.signUpUser(
                            email, password,
                            nextActivityClass = MainScreenActivity::class.java
                        )
                    },
                    onLoginButtonClicked = { email, password ->
                        authManager.loginUser(
                            email, password, MainScreenActivity::class.java
                        )
                    },
                    onGoogleAccountSignUpButtonClicked = { checkAndRequestPermission(this) }
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == GET_ACCOUNTS_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed with accessing accounts
                handleOAuth2Authentication(this)
            } else {
                // Permission denied, handle accordingly
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
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