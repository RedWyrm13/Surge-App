package com.example.surge_app

import android.accounts.AccountManager
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
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.app

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