package com.example.feature_main.domain.usecase

import com.example.feature_main.domain.ApiResponse
import com.example.feature_main.domain.MainFeatureRepository
import com.example.feature_main.domain.entity.Response
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val mainFeatureRepository: MainFeatureRepository
) {

    suspend operator fun invoke(): ApiResponse<Response> {
        return try {
            val response = mainFeatureRepository.getMainFeatureData()
            ApiResponse.Success(response)
        } catch (e: Exception) {
            ApiResponse.Error(e.message.toString())
        }
    }
}