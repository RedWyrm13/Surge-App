package com.example.surge_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.surge_app.ui.theme.LogInOrCreateAccount
import com.example.surge_app.ui.theme.signUpScreen


enum class Screens(){
    loginOrCreateAccount,
    createAccount
}

@Composable
fun SurgeApp(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController,
        startDestination = Screens.loginOrCreateAccount.name){
        composable(route = Screens.loginOrCreateAccount.name){
            LogInOrCreateAccount(
                onLoginButtonClicked = {/* TODO */},
                onSignUpButtonClicked = {navController.navigate(Screens.createAccount.name)}
                )

        }
        composable(route = Screens.createAccount.name){
            signUpScreen()
        }


    }
}