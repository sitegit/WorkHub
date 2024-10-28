package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import javax.inject.Inject

class ObserveFavouriteVacancyUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    operator fun invoke(id: String) = mainRepository.observeIsFavourite(id)
}