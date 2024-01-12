package com.example.surge_app

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.surge_app.ui.theme.LogInOrCreateAccount
import com.example.surge_app.ui.theme.LoginScreen
import com.example.surge_app.ui.theme.SignUpScreen
import com.example.surge_app.ui.theme.SurgeMainScreen
import com.example.surge_app.viewModel.LoginCreateAccountViewModel


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

    val viewModel = viewModel<LoginCreateAccountViewModel>()
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
                loginCreateAccountViewModel = viewModel,
                onSignUpButtonClicked = onSignUpButtonClicked,
                onCancelButtonClicked = {navController.navigate(Screens.LoginOrCreateAccount.name)})
        }
        composable(route = Screens.Login.name){
            LoginScreen(
                loginCreateAccountViewModel = viewModel,
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