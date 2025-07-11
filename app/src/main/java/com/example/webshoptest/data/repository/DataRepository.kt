package com.example.webshoptest.data.repository

import com.example.webshoptest.data.remote.api.DataApi
import com.example.webshoptest.data.remote.dto.DataRequest
import com.example.webshoptest.data.remote.dto.DataResponse
import com.example.webshoptest.domain.repository.IDataRepository
import com.example.webshoptest.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataApi: DataApi
): IDataRepository, BaseRepository() {
    override suspend fun getData(token: String, request: DataRequest): ApiResult<DataResponse> {
       return safeApiCall { dataApi.getData(token, request) }
    }
}