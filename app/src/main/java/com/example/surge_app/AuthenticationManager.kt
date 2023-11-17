package com.example.surge_app

import android.content.Context
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class AuthenticationManager(private val context: Context) {
    private val auth: FirebaseAuth = Firebase.auth


    fun signUpUser(email: String,
                   password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                } else {
                    // User sign-up failed
                    task.exception?.message?.let {
                        Toast.makeText(context, it, Toast.LENGTH_LONG).show()
                    }
                }
            }
    }
}
