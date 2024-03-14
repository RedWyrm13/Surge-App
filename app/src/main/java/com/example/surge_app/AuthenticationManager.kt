package com.example.surge_app

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import android.Manifest
import android.accounts.Account
import android.accounts.AccountManager
import android.app.AlertDialog
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import java.io.InputStream
import java.util.Properties

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
data class ApiKey(val context: Context) {
    val properties = Properties()
    val inputStream: InputStream

    init {
        inputStream = context.assets.open("secrets.properties")
        properties.load(inputStream)
    }

     val apiKey: String = properties.getProperty(context.getString(R.string.places_apikey_new))
}

@Composable
fun CheckAndRequestPermission(
    onPermissionGranted: () -> Unit,
    onPermissionDenied: () -> Unit
) {
    val context = LocalContext.current
    var hasCheckedPermission by remember { mutableStateOf(false) }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    LaunchedEffect(key1 = hasCheckedPermission) {
        if (!hasCheckedPermission) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            } else {
                onPermissionGranted()
            }
            hasCheckedPermission = true
        }
    }
}


//Below this point handles OAuth2 authentication and permission necessary for this from the user
fun handleOAuth2Authentication(context: Context) {
    // Query AccountManager for a list of accounts
    val am: AccountManager = AccountManager.get(context)
    val accounts: Array<out Account> = am.accounts
    Log.d("My Tag", "handleOAuth2Authentication is running")

    // Now you have an array of Google accounts, you can handle further processing
    if (accounts.isNotEmpty()) {
        // Show a dialog to let the user choose an account
        val accountNames = accounts.map { it.name }.toTypedArray()
        AlertDialog.Builder(context)
            .setTitle("Choose an Account")
            .setItems(accountNames) { _, which ->
                // User has selected an account (index: 'which')
                val selectedAccount = accounts[which]
                // Proceed with OAuth2 authentication using 'selectedAccount'
                // For example, you can start an authentication flow here
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
                // Handle cancellation if needed
            }
            .show()
    } else {

        Log.d("My Tag", "No Google Accounts Found")
    }
}

val GET_ACCOUNTS_PERMISSION_CODE = 101 // Any unique code for the permission request

fun checkAndRequestPermission(context: Context) {
    // Check if the permission is not granted
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.GET_ACCOUNTS)
        != PackageManager.PERMISSION_GRANTED
    ) {
        // Permission is not granted, request it
        ActivityCompat.requestPermissions(
            context as Activity,
            arrayOf(Manifest.permission.GET_ACCOUNTS),
            GET_ACCOUNTS_PERMISSION_CODE
        )
    } else {
        // Permission already granted, proceed with accessing accounts
        handleOAuth2Authentication(context)
    }
}
fun checkAccountType(context: Context, accountName: String) {
    Log.d("My Tag", "checkAccountType running!")
    val am: AccountManager = AccountManager.get(context)
    val accounts: Array<out Account> = am.getAccounts()

    for (account in accounts) {
        if (account.name == accountName) {
            Log.d("My Tag", "Account Type for $accountName: ${account.type}")
            break
        }
    }
}