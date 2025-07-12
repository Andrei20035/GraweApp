package com.example.webshoptest.ui.screens.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webshoptest.data.local.SecureTokenManager
import com.example.webshoptest.data.remote.dto.LoginRequest
import com.example.webshoptest.data.repository.LoginRepository
import com.example.webshoptest.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val secureTokenManager: SecureTokenManager
): ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username = username) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun togglePasswordVisibility() {
        _uiState.update { it.copy(isPasswordVisible = !it.isPasswordVisible) }
    }

    fun login() {
        val username = _uiState.value.username
        val password = _uiState.value.password

        if(username.isEmpty() || password.isEmpty()) {
            setError("Username or password cannot be empty.")
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            val request = LoginRequest(
                username = username,
                password = password
            )

            when(val result = loginRepository.login(request)) {
                is ApiResult.Success -> {
                    secureTokenManager.saveToken(result.data)
                    _uiState.update { it.copy(isLoading = false, isAuthenticated = true) }
                }
                is ApiResult.Error -> setError(result.message)
            }
        }
    }

    private fun setError(message: String) {
        Log.d("ERROR", message)
        _uiState.update { it.copy(errorMessage = message, isLoading = false) }

        viewModelScope.launch {
            delay(5000)
            _uiState.update { it.copy(errorMessage = null) }
        }
    }
}