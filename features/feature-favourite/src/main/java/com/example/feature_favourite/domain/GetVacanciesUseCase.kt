package com.example.feature_favourite.domain

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) {

    operator fun invoke(): Flow<List<Vacancy>> {
        return favouriteRepository.favouriteVacancies
    }
}