package com.example.feature_favourite.domain

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
