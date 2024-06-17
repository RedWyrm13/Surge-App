package com.example.surge_app

import android.accounts.AccountManager
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.surge_app.network.PlacesApiManager
import com.example.surge_app.ui.theme.SurgeAppTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.app


class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        initializePassengerApp()
        initializeDriverApp()
    }
    private fun initializePassengerApp(){
        if (FirebaseApp.getApps(this).isEmpty()){
            FirebaseApp.initializeApp(this)
            FirebaseFirestore.getInstance()
        }
    }
    private fun initializeDriverApp(){
        if(FirebaseApp.getApps(this).none{it.name == "DriverApp"}){
            val options = FirebaseOptions.Builder()
                .setApplicationId("1:201633923016:android:617388de46b571d4774afd")
                .setApiKey("AIzaSyDsSGmGIHxU-BMU-R0XB3DsvjQ0jGBXFhM")
                .setProjectId("surgedatabasefordrivers")
                .build()
            FirebaseApp.initializeApp(this, options, "DriverApp")
            FirebaseFirestore.getInstance(FirebaseApp.getInstance("DriverApp"))
        }
    }
}
class LoginCreateAccountActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

         val chooseAccountLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                // Handle the selected account here
                val accountName = result.data?.getStringExtra(AccountManager.KEY_ACCOUNT_NAME)
                // Do something with the selected account
            }
        }
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        FirebaseFirestore.getInstance()

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
                    onGoogleAccountSignUpButtonClicked = { handleOAuth2Authentication(this, chooseAccountLauncher) }
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
                SurgeMain(context = context,)
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