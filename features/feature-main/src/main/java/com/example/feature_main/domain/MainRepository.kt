package com.example.feature_main.domain

import com.example.feature_main.domain.entity.Response
import com.example.feature_main.domain.entity.Vacancy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MainRepository {

    val vacanciesFlow: StateFlow<Response>

    suspend fun getMainFeatureData()

    fun observeIsFavourite(id: String): Flow<Boolean>

    suspend fun addVacancyToFavourite(vacancy: Vacancy)

    suspend fun removeVacancyFromFavourite(id: String)

    suspend fun updateVacancies()
}