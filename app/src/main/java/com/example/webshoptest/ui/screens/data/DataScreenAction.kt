package com.example.webshoptest.ui.screens.data

import com.example.webshoptest.domain.model.CountryItem
import com.example.webshoptest.domain.model.InsuranceOffer
import java.time.LocalDate

sealed class DataScreenAction {
    data class SetOffers(val offers: List<InsuranceOffer>): DataScreenAction()
    data class SetInsuranceCoverage(val coverage: InsuranceCoverage) : DataScreenAction()
    data class SetTravelReason(val reason: TravelReason) : DataScreenAction()
    data class SetBirthDate(val birthDate: LocalDate) : DataScreenAction()
    data class SetCoverageArea(val coverage: CoverageArea) : DataScreenAction()
    data class SetCountryCode(val countryCode: String) : DataScreenAction()
    data class SetStartDate(val begin: LocalDate) : DataScreenAction()
    data class SetEndDate(val end: LocalDate) : DataScreenAction()
    object SetFullYear : DataScreenAction()
    object SetPandemicProtection : DataScreenAction()
    object SetSki : DataScreenAction()
    object GetOffers : DataScreenAction()
}