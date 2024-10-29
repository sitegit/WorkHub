package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.ApiResponse
import com.example.feature_main.domain.MainRepository
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(): ApiResponse<*> {
        return try {
            mainRepository.getMainFeatureData()
            ApiResponse.Success("")
        } catch (e: Exception) {
            ApiResponse.Error("Произошла ошибка при загрузке данных ${e.message}")
        }
    }
}