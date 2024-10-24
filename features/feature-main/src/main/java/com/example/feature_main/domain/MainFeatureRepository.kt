package com.example.feature_main.domain

import com.example.feature_main.domain.entity.Response

interface MainFeatureRepository {

    suspend fun getMainFeatureData(): Response
}