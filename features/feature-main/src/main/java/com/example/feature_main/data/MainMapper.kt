package com.example.feature_main.data

import com.example.core_db.VacancyDb
import com.example.core_network.model.OfferDto
import com.example.core_network.model.ResponseDto
import com.example.core_network.model.VacancyDto
import com.example.feature_main.domain.entity.Offer
import com.example.feature_main.domain.entity.Response
import com.example.feature_main.domain.entity.Vacancy

fun ResponseDto.toEntity() = Response(
    vacancies = vacancies.map { it.toEntity() },
    offers = offers.map { it.toEntity() }
)

fun OfferDto.toEntity() = Offer(
    id = id,
    title = title,
    link = link,
    button = button?.text
)

fun VacancyDto.toEntity() = Vacancy(
    id = id,
    lookingNumber = lookingNumber,
    title = title,
    address = address.town,
    company = company,
    experience = experience.previewText,
    publishedDate = publishedDate,
    isFavorite = isFavorite
)

fun Vacancy.toVacancyDb() = VacancyDb(
    id = id,
    lookingNumber = lookingNumber,
    title = title,
    address = address,
    company = company,
    experience = experience,
    publishedDate = publishedDate,
    isFavorite = isFavorite
)