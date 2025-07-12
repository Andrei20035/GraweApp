package com.example.webshoptest.ui.navigation

sealed class Screen(val route: String) {
    object Login: Screen("login")
    object Data: Screen("data")
    object Offers: Screen("offers")
}