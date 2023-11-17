package com.example.surge_app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.surge_app.ui.theme.LogInOrCreateAccount
import com.example.surge_app.ui.theme.LoginScreen
import com.example.surge_app.ui.theme.SignUpScreen


enum class Screens{
    LoginOrCreateAccount,
    SignUp,
    Login,
}

@Composable
fun SurgeApp(
    navController: NavHostController = rememberNavController()){
    NavHost(navController = navController,
        startDestination = Screens.LoginOrCreateAccount.name){
        composable(route = Screens.LoginOrCreateAccount.name){
            LogInOrCreateAccount(
                onLoginButtonClicked = {navController.navigate(Screens.Login.name)},
                onSignUpButtonClicked = {navController.navigate(Screens.SignUp.name)}
                )

        }
        composable(route = Screens.SignUp.name){
            SignUpScreen(
                onSignUpButtomClicked = {/* TO DO */},
                onCancelButtonClicked = {navController.navigate(Screens.LoginOrCreateAccount.name)})
        }
        composable(route = Screens.Login.name){
            LoginScreen(
                onLoginButtonClicked = {/* TO DO */},
                onSignUpButtonClicked = {navController.navigate(Screens.SignUp.name)}
            )
        }


    }
}