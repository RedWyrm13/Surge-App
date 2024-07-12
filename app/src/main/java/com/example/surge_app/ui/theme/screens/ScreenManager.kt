package com.example.surge_app.ui.theme.screens

import android.content.Context
import android.content.SharedPreferences

class ScreenManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun saveLastScreen(screen: String) {
        prefs.edit().putString("last_screen", screen).apply()
    }

    fun getLastScreen(): String? {
        return prefs.getString("last_screen", "LoginOrCreateAccountScreen") // Default to "Home" if not found
    }

    fun clearLastScreen() {
        prefs.edit().remove("last_screen").apply()
    }
}
