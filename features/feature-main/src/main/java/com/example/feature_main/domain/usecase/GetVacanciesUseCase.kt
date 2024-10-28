package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke() {
        mainRepository.getMainFeatureData()
    }
}