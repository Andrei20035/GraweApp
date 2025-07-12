package com.example.webshoptest.ui.screens.data

import com.example.webshoptest.domain.model.InsuranceOffer
import java.time.LocalDate

data class DataUiState(
    val offers: List<InsuranceOffer> = emptyList(),

    val fullYear: Boolean = false,
    val insuranceCoverage: InsuranceCoverage = InsuranceCoverage.INDIVIDUAL,
    val pandemicProtection: Boolean = false,
    val ski: Boolean = false,
    val travelReason: TravelReason = TravelReason.TOURISTIC,
    val birthDate: LocalDate? = null,
    val coverageArea: CoverageArea = CoverageArea.EUROPE,
    val countryCode: String = "RO",
    val insuranceBeginDate: LocalDate? = null,
    val insuranceEndDate: LocalDate? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null
)