package com.example.feature_favourite.data

import com.example.core_db.VacancyDb
import com.example.feature_favourite.domain.Vacancy

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

fun VacancyDb.toEntity() = Vacancy(
    id = id,
    lookingNumber = lookingNumber,
    title = title,
    address = address,
    company = company,
    experience = experience,
    publishedDate = publishedDate,
    isFavorite = isFavorite
)

fun List<VacancyDb>.toEntities(): List<Vacancy> = map { it.toEntity() }