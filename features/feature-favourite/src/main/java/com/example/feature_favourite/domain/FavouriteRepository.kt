package com.example.feature_favourite.domain

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteVacancies: Flow<List<Vacancy>>

    suspend fun removeFromFavourite(vacancyId: String)
}