package com.example.webshoptest.utils

import kotlinx.serialization.Serializable

sealed class ApiResult<out T> {
    @Serializable
    data class Success<out T>(val data: T): ApiResult<T>()
    @Serializable
    data class Error(val message: String): ApiResult<Nothing>()
}