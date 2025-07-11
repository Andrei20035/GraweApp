package com.example.webshoptest.data.remote.api

import com.example.webshoptest.data.remote.dto.DataRequest
import com.example.webshoptest.data.remote.dto.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface DataApi {
    @POST("WSTravel/VratiPremije")
    suspend fun getData(
        @Header("Authorization") token: String,
        @Body request: DataRequest
    ): Response<DataResponse>
}