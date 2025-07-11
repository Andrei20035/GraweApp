package com.example.webshoptest.domain.repository

import com.example.webshoptest.data.remote.dto.LoginRequest
import com.example.webshoptest.utils.ApiResult

interface ILoginRepository {
    suspend fun login(request: LoginRequest): ApiResult<String>
}