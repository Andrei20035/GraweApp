package com.example.webshoptest.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.webshoptest.ui.screens.data.DataScreen
import com.example.webshoptest.ui.screens.data.DataViewModel
import com.example.webshoptest.ui.screens.login.LoginScreen
import com.example.webshoptest.ui.screens.offers.OffersScreen

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
        navigation(
            startDestination = Screen.Data.route,
            route = "data_offers_flow"
        ) {
            composable(Screen.Data.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("data_offers_flow")
                }
                val sharedViewModel: DataViewModel = hiltViewModel(parentEntry)
                DataScreen(
                    viewModel = sharedViewModel,
                    navController = navController
                )
            }
            composable(Screen.Offers.route) {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("data_offers_flow")
                }
                val sharedViewModel: DataViewModel = hiltViewModel(parentEntry)
                OffersScreen(
                    viewModel = sharedViewModel
                )
            }
        }
    }
}