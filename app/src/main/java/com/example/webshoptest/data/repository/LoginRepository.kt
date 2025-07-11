package com.example.webshoptest.data.repository

import com.example.webshoptest.data.remote.api.LoginApi
import com.example.webshoptest.data.remote.dto.LoginRequest
import com.example.webshoptest.domain.repository.ILoginRepository
import com.example.webshoptest.utils.ApiResult
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LoginRepository @Inject constructor(
    private val loginApi: LoginApi
): ILoginRepository, BaseRepository() {
    override suspend fun login(request: LoginRequest): ApiResult<String> {
        return safeApiCall { loginApi.login(request) }
    }
}