package com.example.webshoptest.ui.screens.login

data class LoginUiState(
    val username: String = "wsTestGrawe",
    val password: String = "TEstGrawe.9545w9tr98",
    val isPasswordVisible: Boolean = false,

    val isAuthenticated: Boolean = false,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)