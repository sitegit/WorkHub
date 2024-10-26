package com.example.feature_main.domain.entity

data class Vacancy(
    val id: String,
    val lookingNumber: Int?,
    val title: String,
    val address: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
    val isFavorite: Boolean,
)
