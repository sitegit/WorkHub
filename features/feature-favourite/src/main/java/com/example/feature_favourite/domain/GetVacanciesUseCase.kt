package com.example.feature_favourite.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetVacanciesUseCase @Inject constructor(
    private val favouriteRepository: FavouriteRepository
) {

    operator fun invoke(): Flow<DbResponse<List<Vacancy>>> = flow {
        try {
            favouriteRepository.favouriteVacancies.collect { vacancies ->
                emit(DbResponse.Success(vacancies))
            }
        } catch (e: Exception) {
            emit(DbResponse.Error(e.message.toString()))
        }
    }
}