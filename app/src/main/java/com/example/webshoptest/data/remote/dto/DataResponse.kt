package com.example.webshoptest.data.remote.dto

import com.example.webshoptest.domain.model.Insurance
import kotlinx.serialization.Serializable

@Serializable
data class DataResponse(
    val insurances: List<Insurance>
)
