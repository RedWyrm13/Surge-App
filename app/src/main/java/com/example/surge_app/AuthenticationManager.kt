package com.example.surge_app

import android.Manifest
import android.accounts.AccountManager
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
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
                    // We create a JWT for the user which is managed by Firebase
                    val user = auth.currentUser
                    user?.getIdToken(true)?.addOnCompleteListener{tokenTask ->
                        if (tokenTask.isSuccessful){
                            val idToken = tokenTask.result?.token
                            saveToken(token = idToken.toString(), context = currentActivity)
                        }
                        else{
                            Log.e("MyTag_AuthenticationManager.kt", "Error getting token: ", tokenTask.exception)
                        }
                    }
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
            onPermissionGranted() //This is why the app needs to be restarted after granting permission. The app does not do anything once permission is granted.
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


fun handleOAuth2Authentication(context: Context, chooseAccountLauncher: ActivityResultLauncher<Intent>){
    Log.d("My Tag", "handleOAuthentication is running")
    launchAccountPicker(context, chooseAccountLauncher)

}
fun launchAccountPicker(context: android.content.Context, chooseAccountLauncher: ActivityResultLauncher<Intent>) {
    Log.d("My Tag", "launchAccountPicker is running")

    val intent = AccountManager.newChooseAccountIntent(null, null, arrayOf("com.google"), null, null, null, null)
    chooseAccountLauncher.launch(intent)
}

fun saveToken(context: Context, token: String?) {
    if (token != null) {
        // Create or retrieve the MasterKey for encryption
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        // Initialize EncryptedSharedPreferences with the MasterKey
        val sharedPreferences = EncryptedSharedPreferences.create(
            context,
            "secure_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        // Save the token securely
        sharedPreferences.edit().putString("auth_token", token).apply()
    }
}

fun getToken(context: Context): String? {
    // Create or retrieve the MasterKey for decryption
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    // Retrieve EncryptedSharedPreferences with the MasterKey
    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Retrieve the token securely
    return sharedPreferences.getString("auth_token", null)
}

fun logout(context: Context) {
    val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    val sharedPreferences = EncryptedSharedPreferences.create(
        context,
        "secure_prefs",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    // Clear the token
    sharedPreferences.edit().remove("auth_token").apply()

    // Redirect to login screen
    val intent = Intent(context, LoginCreateAccountActivity::class.java)
    context.startActivity(intent)
    (context as Activity).finish()
}