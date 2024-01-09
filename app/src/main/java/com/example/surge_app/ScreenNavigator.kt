package com.example.surge_app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.surge_app.ui.theme.LogInOrCreateAccount
import com.example.surge_app.ui.theme.LoginScreen
import com.example.surge_app.ui.theme.SignUpScreen
import com.example.surge_app.ui.theme.SurgeMainScreen


enum class Screens{
    LoginOrCreateAccount,
    SignUp,
    Login,
    MainScreen,
}

@Composable
fun SurgeApp(
    onSignUpButtonClicked: (String, String) -> Unit,
    onLoginButtonClicked: (String, String)-> Unit,
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
                onSignUpButtonClicked = onSignUpButtonClicked,
                onCancelButtonClicked = {navController.navigate(Screens.LoginOrCreateAccount.name)})
        }
        composable(route = Screens.Login.name){
            LoginScreen(
                onLoginButtonClicked = onLoginButtonClicked,
                onSignUpButtonClicked = {navController.navigate(Screens.SignUp.name)}
            )
        }



    }
}
@Composable
fun SurgeMain(
    navController: NavHostController = rememberNavController(),
    context: Context){

    NavHost(navController = navController,
        startDestination = Screens.MainScreen.name){
        composable(route = Screens.MainScreen.name){
            SurgeMainScreen(context = context)
        }
    }
}