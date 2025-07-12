package com.example.webshoptest.domain.model
import kotlinx.serialization.Serializable

@Serializable
data class InsuranceOffer(
    val productVariant: Int,
    val amountInsured: Int,
    val premiumEur: Double,
    val premiumRsd: Double,
    val cancelTrip: Boolean,
    val cancellationPremiumEur: Double,
    val cancellationPremiumRsd: Double,
    val type: Int,
    val pandemicProtectionIncluded: Boolean
)
