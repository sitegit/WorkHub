package com.example.core_db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_vacancies")
data class VacancyDb(
    @PrimaryKey val id: String,
    val lookingNumber: Int?,
    val title: String,
    val address: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
    val isFavorite: Boolean,
)
