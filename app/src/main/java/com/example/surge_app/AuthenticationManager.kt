package com.example.surge_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AuthenticationManager(private val context: Context, private val currentActivity: Activity) {
    private val auth: FirebaseAuth = Firebase.auth

    fun signUpUser(email: String, password: String, nextActivityClass: Class<*>) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // User sign-up successful, start new activity
                    context.startActivity(Intent(context, nextActivityClass))
                    // Finish the current activity
                    currentActivity.finish()
                } else {
                    // User sign-up failed
                    task.exception?.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }

    fun loginUser(email: String, password: String, nextActivityClass: Class<*>) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(currentActivity) { task ->
                if (task.isSuccessful) {
                    // User login successful, start new activity
                    val intent = Intent(context, nextActivityClass)
                    context.startActivity(intent)
                    // Finish the current activity
                    currentActivity.finish()
                } else {
                    // User login failed
                    task.exception?.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
