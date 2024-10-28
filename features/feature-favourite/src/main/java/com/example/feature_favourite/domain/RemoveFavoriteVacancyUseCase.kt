package com.example.feature_favourite.domain

import javax.inject.Inject

class RemoveFavoriteVacancyUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) {

    suspend operator fun invoke(vacancyId: String) {
        favouriteRepository.removeFromFavourite(vacancyId)
    }
}