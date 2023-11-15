package com.example.surge_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.surge_app.ui.theme.LogInOrCreateAccount
import com.example.surge_app.ui.theme.SignUpScreen


enum class Screens{
    LoginOrCreateAccount,
    SignUp,
    Login,
}

@Composable
fun SurgeApp(navController: NavHostController = rememberNavController()){
    NavHost(navController = navController,
        startDestination = Screens.LoginOrCreateAccount.name){
        composable(route = Screens.LoginOrCreateAccount.name){
            LogInOrCreateAccount(
                onLoginButtonClicked = {/* TODO */},
                onSignUpButtonClicked = {navController.navigate(Screens.SignUp.name)}
                )

        }
        composable(route = Screens.SignUp.name){
            SignUpScreen()
        }
        composable(route = Screens.Login.name){
            //loginScreen()
        }


    }
}