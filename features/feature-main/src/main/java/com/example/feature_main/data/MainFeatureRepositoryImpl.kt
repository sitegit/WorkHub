package com.example.feature_main.data

import com.example.core_network.ApiService
import com.example.feature_main.domain.MainFeatureRepository
import com.example.feature_main.domain.entity.Response
import javax.inject.Inject

class MainFeatureRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MainFeatureRepository{

    override suspend fun getMainFeatureData(): Response {
        return apiService.getMockData().toEntity()
    }
}

