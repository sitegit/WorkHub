package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import com.example.feature_main.domain.entity.Vacancy
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ToggleFavoriteVacancyUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(vacancy: Vacancy) {
        val isFavorite = mainRepository.observeIsFavourite(vacancy.id).first()

        if (!isFavorite) {
            mainRepository.addVacancyToFavourite(vacancy.copy(isFavorite = true))
        } else {
            mainRepository.removeVacancyFromFavourite(vacancy.id)
        }

        mainRepository.updateVacancies()
    }
}