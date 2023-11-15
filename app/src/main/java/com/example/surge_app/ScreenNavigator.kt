package com.example.surge_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController


enum class Screens(){
    loginOrCreateAccount,
    createAccount
}

@Composable
fun SurgeApp(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController,
        startDestination = Screens.loginOrCreateAccount.name){


    }
}