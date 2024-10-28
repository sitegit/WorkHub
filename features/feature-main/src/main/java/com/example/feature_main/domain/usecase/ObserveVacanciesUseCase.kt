package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.MainRepository
import com.example.feature_main.domain.entity.Response
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveVacanciesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(): StateFlow<Response> {
        return mainRepository.vacanciesFlow
    }
}