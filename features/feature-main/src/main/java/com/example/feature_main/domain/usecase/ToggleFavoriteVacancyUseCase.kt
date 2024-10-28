package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import com.example.feature_main.domain.entity.Vacancy
import javax.inject.Inject

class ToggleFavoriteVacancyUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(vacancy: Vacancy) {
        mainRepository.toggleFavoriteVacancy(vacancy)
    }
}