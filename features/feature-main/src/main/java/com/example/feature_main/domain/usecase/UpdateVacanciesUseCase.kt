package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import javax.inject.Inject

class UpdateVacanciesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke() {
        mainRepository.updateVacancies()
    }
}