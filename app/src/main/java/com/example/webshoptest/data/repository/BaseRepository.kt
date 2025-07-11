package com.example.webshoptest.data.repository

import com.example.webshoptest.utils.ApiResult
import retrofit2.Response

abstract class BaseRepository {
    protected suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): ApiResult<T> {
        return try {
            val response = apiCall()
            if(response.isSuccessful) {
                val body = response.body()
                if(body != null) {
                    ApiResult.Success(body)
                } else {
                    ApiResult.Error("Empty response")
                }
            } else {
                val errorMessage = response.errorBody()?.string() ?: "Unknown error"
                ApiResult.Error(errorMessage)
            }
        } catch (e: Exception) {
            ApiResult.Error("Unexpected error: ${e.message}")
        }
    }
}