package com.example.core_network.model

data class ResponseDto(
    val offers: List<OfferDto>,
    val vacancies: List<VacancyDto>
)
