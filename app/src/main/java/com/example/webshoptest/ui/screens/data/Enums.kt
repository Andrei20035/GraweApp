package com.example.webshoptest.ui.screens.data

enum class TravelReason(val code: String, val displayName: String) {
    TOURISTIC("1", "Touristic"),
    BUSINESS("2", "Business"),
    STUDY("3", "Study"),
    TEMPORARY_WORK_ABROAD("4", "Temporary Work Abroad")
}

enum class InsuranceCoverage(val code: String, val displayName: String) {
    INDIVIDUAL("1", "Individual"),
    FAMILY("2", "Family")
}

enum class CoverageArea(val code: String, val displayName: String) {
    EUROPE("1", "Europe"),
    WORLD("2", "World")
}
