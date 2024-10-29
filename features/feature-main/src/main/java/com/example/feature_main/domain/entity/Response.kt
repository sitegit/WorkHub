package com.example.feature_main.domain.entity

data class Response(
    val vacancies: List<Vacancy>,
    val offers: List<Offer>
)