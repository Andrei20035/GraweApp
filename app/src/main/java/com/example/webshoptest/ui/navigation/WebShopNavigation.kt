package com.example.webshoptest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.webshoptest.ui.screens.login.LoginScreen

@Composable
fun WebShopNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController
            )
        }
        composable(Screen.Data.route) {
//            DataScreen()
        }
    }
}