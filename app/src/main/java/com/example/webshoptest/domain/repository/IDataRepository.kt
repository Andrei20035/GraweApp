package com.example.webshoptest.domain.repository

import com.example.webshoptest.data.remote.dto.DataRequest
import com.example.webshoptest.domain.model.InsuranceOffer
import com.example.webshoptest.utils.ApiResult

interface IDataRepository {
    suspend fun getData(token: String, request: DataRequest): ApiResult<List<InsuranceOffer>>
}