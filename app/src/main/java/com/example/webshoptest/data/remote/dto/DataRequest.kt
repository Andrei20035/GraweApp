package com.example.webshoptest.data.remote.dto
import kotlinx.serialization.Serializable

@Serializable
data class DataRequest(
    val tariff: Tariff
)

@Serializable
data class Tariff(
    val fullYear: Boolean,
    val insuranceCoverage: Int, // 1 = individual, 2 = family
    val pandemicProtectionIncluded: Boolean,
    val ski: Boolean,
    val travelReason: Int, //  1 Turisticko; 2 Poslovno; 3 Studijsko; 4 Privremen rad u inostranstvu
    val calculationInsureeAgeRange: Int, // < 71 then 0, between 71 and 81 then 1, > 81 then 2
    val insuranceCoverageArea: Int? = null, // 1 - europe, 2 - World   //  Optional if CountryCode is used
    val countryCode: String? = null, // Optional alternative to InsuranceCoverageArea
    val insuranceBeginDate: String,
    val insuranceEndDate: String
)
