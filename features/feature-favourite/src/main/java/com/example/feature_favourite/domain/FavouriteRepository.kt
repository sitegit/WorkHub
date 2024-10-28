package com.example.feature_favourite.domain

import kotlinx.coroutines.flow.Flow

interface FavouriteRepository {

    val favouriteVacancies: Flow<List<Vacancy>>

    fun observeIsFavourite(vacancyId: String): Flow<Boolean>

    suspend fun removeFromFavourite(vacancyId: String)
}