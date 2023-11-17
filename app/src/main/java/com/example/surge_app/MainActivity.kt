package com.example.surge_app

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.surge_app.ui.theme.SurgeAppTheme
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = Firebase.auth

        setContent {
                SurgeAppTheme {
                    SurgeApp(onSignUpButtonClicked = { email, password ->
                        signUpUser(email, password)
                    }
                    )
                }
        }
    }
    private fun signUpUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                } else {
                    // User sign-up failed
                    task.exception?.message?.let {
                        Toast.makeText(this, it, Toast.LENGTH_LONG).show()
                    }
                }
            }

    }
}


