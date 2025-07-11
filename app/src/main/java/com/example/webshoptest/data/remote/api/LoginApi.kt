package com.example.webshoptest.data.remote.api

import com.example.webshoptest.data.remote.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApi {
    @POST("user/getTokenWebShop")
    suspend fun login(@Body loginRequest: LoginRequest): Response<String>
}