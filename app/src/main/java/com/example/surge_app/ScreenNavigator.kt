package com.example.surge_app

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.surge_app.data.repositories.RideRepoImpl
import com.example.surge_app.ui.theme.screens.GoogleAccountSignUpScreen
import com.example.surge_app.ui.theme.screens.LogInOrCreateAccount
import com.example.surge_app.ui.theme.screens.LoginScreen
import com.example.surge_app.ui.theme.screens.SignUpScreen
import com.example.surge_app.ui.theme.screens.StartRideScreen
import com.example.surge_app.ui.theme.screens.SurgeMainScreen
import com.example.surge_app.ui.theme.viewModel.DestinationViewModel
import com.example.surge_app.ui.theme.viewModel.LocationViewModel
import com.example.surge_app.ui.theme.viewModel.LoginCreateAccountViewModel
import com.example.surge_app.ui.theme.viewModel.RideViewModel


enum class Screens{
    LoginOrCreateAccount,
    SignUp,
    GoogleAccountSignUp,
    Login,
    MainScreen,
    StartRideScreen,
}

@Composable
fun SurgeApp(
    onSignUpButtonClicked: (String, String) -> Unit,
    onLoginButtonClicked: (String, String)-> Unit,
    onGoogleAccountSignUpButtonClicked: () -> Unit,
    navController: NavHostController = rememberNavController()){

    val viewModel = viewModel<LoginCreateAccountViewModel>()
    NavHost(navController = navController,
        startDestination = Screens.LoginOrCreateAccount.name){
        composable(route = Screens.LoginOrCreateAccount.name){
            LogInOrCreateAccount(
                onLoginButtonClicked = {navController.navigate(Screens.Login.name)},
                onSignUpButtonClicked = {navController.navigate(Screens.SignUp.name)},
                onGoogleAccountSignUpButtonClicked = {navController.navigate(Screens.GoogleAccountSignUp.name)}
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
        composable(route = Screens.GoogleAccountSignUp.name){
            GoogleAccountSignUpScreen(
                onGoogleSignUpButtonClicked = onGoogleAccountSignUpButtonClicked,
                onCancelButtonClicked = {navController.navigate(Screens.LoginOrCreateAccount.name)}
                )
        }
    }
}
@Composable
fun SurgeMain(
    navController: NavHostController = rememberNavController(),
    context: Context
) {
    val rideRepoImpl = RideRepoImpl()
    val locationViewModel = viewModel<LocationViewModel>()
    val destinationViewModel = DestinationViewModel(rideRepoImpl = rideRepoImpl)
    val rideViewModel = RideViewModel(rideRepoImpl = rideRepoImpl)

    // SharedPreferences for saving and retrieving the last screen
    val sharedPreferences = context.getSharedPreferences("SurgeAppPrefs", Context.MODE_PRIVATE)
    val savedRoute = sharedPreferences.getString("lastScreen", Screens.MainScreen.name)

    // Navigate to the saved route if it exists
    LaunchedEffect(Unit) {
        navController.navigate(savedRoute ?: Screens.MainScreen.name)
    }

    // Observe changes in the current back stack entry to save the route
    NavHost(navController = navController, startDestination = Screens.MainScreen.name) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            sharedPreferences.edit().putString("lastScreen", destination.route).apply()
        }

        composable(route = Screens.MainScreen.name) {
            SurgeMainScreen(
                onRideButtonClicked = { navController.navigate(Screens.StartRideScreen.name) },
                locationViewModel = locationViewModel,
                destinationViewModel = destinationViewModel,
                rideViewModel = rideViewModel
            )
        }
        composable(route = Screens.StartRideScreen.name) {
            StartRideScreen(
                rideViewModel = rideViewModel
            )
        }
    }
}
