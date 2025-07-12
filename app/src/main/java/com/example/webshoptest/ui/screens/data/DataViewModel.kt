package com.example.webshoptest.ui.screens.data

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.webshoptest.data.local.SecureTokenManager
import com.example.webshoptest.data.remote.dto.DataRequest
import com.example.webshoptest.data.remote.dto.Tariff
import com.example.webshoptest.data.repository.DataRepository
import com.example.webshoptest.domain.model.CountryItem
import com.example.webshoptest.domain.model.InsuranceOffer
import com.example.webshoptest.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(
    private val dataRepository: DataRepository,
    private val secureTokenManager: SecureTokenManager
): ViewModel() {
    private val _uiState = MutableStateFlow(DataUiState())
    val uiState: StateFlow<DataUiState> = _uiState.asStateFlow()

    private val _countries = MutableStateFlow<List<CountryItem>>(emptyList())
    val countries: StateFlow<List<CountryItem>> = _countries.asStateFlow()

    init {
        _countries.value = Locale.getISOCountries().map { code ->
            val locale = Locale("", code)
            CountryItem(code = code, name = locale.displayCountry)
        }.sortedBy { it.name }
    }

    fun setOffers(offers: List<InsuranceOffer>) {
        _uiState.update { it.copy(offers = offers) }
    }

    fun setFullYear() {
        _uiState.update { it.copy(fullYear = !it.fullYear) }
    }

    fun setInsuranceCoverage(coverage: InsuranceCoverage) {
        _uiState.update { it.copy(insuranceCoverage = coverage) }
    }

    fun setPandemicProtection() {
        _uiState.update { it.copy(pandemicProtection = !it.pandemicProtection) }
    }

    fun setSki() {
        _uiState.update { it.copy(ski = !it.ski) }
    }

    fun setTravelReason(reason: TravelReason) {
        _uiState.update { it.copy(travelReason = reason) }
    }

    fun setBirthDate(birthDate: LocalDate) {
        _uiState.update { it.copy(birthDate = birthDate) }
    }

    fun setCoverageArea(coverage: CoverageArea) {
        _uiState.update { it.copy(coverageArea = coverage) }
    }

    fun setCountryCode(countryCode: String) {
        _uiState.update { it.copy(countryCode = countryCode) }
    }

    fun setStartDate(begin: LocalDate) {
        _uiState.update { it.copy(insuranceBeginDate = begin) }
    }

    fun setEndDate(end: LocalDate) {
        _uiState.update { it.copy(insuranceEndDate = end) }
    }

    fun getOffers() {
        viewModelScope.launch {
            if(!verifyInfo()) return@launch

            _uiState.update { it.copy(isLoading = true) }
            val token = secureTokenManager.getToken()

            if(token == null) {
                setError("JWT token is null")
                return@launch
            }
            val request = buildDataRequestFromState(_uiState.value)

            when(val result = dataRepository.getData(token, request)) {
                is ApiResult.Success -> setOffers(result.data)
                is ApiResult.Error -> setError(result.message)
            }

            _uiState.update { it.copy(isLoading = false) }
        }
    }

    private fun verifyInfo(): Boolean {
        val birthDate = _uiState.value.birthDate
        val beginDate = _uiState.value.insuranceBeginDate
        val endDate = _uiState.value.insuranceEndDate

        if(birthDate == null) {
            setError("Birthdate cannot be null")
            return false
        }

        if(birthDate.isAfter(LocalDate.now())) {
            setError("You cannot be in the future")
            return false
        }

        if(beginDate == null || endDate == null) {
            setError("Please specify your desired period")
            return false
        }

        if (beginDate.isAfter(endDate)) {
            setError("Start date must be before end date")
            return false
        }

        if (beginDate == endDate) {
            setError("Start and end date cannot be the same")
            return false
        }

        if(!beginDate.isAfter(LocalDate.now())) {
            setError("The start date must be at least tomorrow!")
            return false
        }
        return true
    }

    private fun buildDataRequestFromState(state: DataUiState): DataRequest {
        return DataRequest(
            tariff = Tariff(
                fullYear = state.fullYear,
                insuranceCoverage = state.insuranceCoverage.code.toInt(),
                pandemicProtectionIncluded = state.pandemicProtection,
                ski = state.ski,
                travelReason = state.travelReason.code.toInt(),
                calculationInsureeAgeRange = calculateAgeRange(state.birthDate!!),
                insuranceCoverageArea = state.coverageArea.code.toInt(),
                countryCode = state.countryCode,
                insuranceBeginDate = state.insuranceBeginDate.toString(),
                insuranceEndDate = state.insuranceEndDate.toString()
            )
        )
    }

    private fun calculateAgeRange(birthDate: LocalDate): Int {
        val age = Period.between(birthDate, LocalDate.now()).years
        return when {
            age < 71 -> 0
            age in 71..81 -> 1
            else -> 2
        }
    }

    private fun setError(message: String) {
        Log.d("ERROR", message)
        _uiState.update { it.copy(errorMessage = message, isLoading = false) }

        viewModelScope.launch {
            delay(5000)
            _uiState.update { it.copy(errorMessage = null) }
        }
    }
}