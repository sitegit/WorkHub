package com.example.feature_main.data

import com.example.core_network.model.AddressDto
import com.example.core_network.model.ExperienceDto
import com.example.core_network.model.OfferDto
import com.example.core_network.model.ResponseDto
import com.example.core_network.model.SalaryDto
import com.example.core_network.model.VacancyDto
import com.example.feature_main.domain.entity.Address
import com.example.feature_main.domain.entity.Experience
import com.example.feature_main.domain.entity.Offer
import com.example.feature_main.domain.entity.Response
import com.example.feature_main.domain.entity.Salary
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
    address = address.toEntity(),
    company = company,
    experience = experience.toEntity(),
    publishedDate = publishedDate,
    isFavorite = isFavorite,
    salary = salary.toEntity(),
    schedules = schedules,
    appliedNumber = appliedNumber,
    description = description,
    responsibilities = responsibilities,
    questions = questions
)

fun AddressDto.toEntity() = Address(
    town = town,
    street = street,
    house = house
)

fun ExperienceDto.toEntity() = Experience(
    previewText = previewText,
    text = text
)

fun SalaryDto.toEntity() = Salary(
    full = full,
    short = short
)