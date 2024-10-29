package com.example.feature_favourite.data

import com.example.core_db.FavouriteVacanciesDao
import com.example.feature_favourite.domain.FavouriteRepository
import com.example.feature_favourite.domain.Vacancy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavouriteRepositoryImpl @Inject constructor(
    private val vacanciesDao: FavouriteVacanciesDao
) : FavouriteRepository {

    override val favouriteVacancies: Flow<List<Vacancy>>
        get() = vacanciesDao.getFavouriteVacancies().map { it.toEntities() }

    override suspend fun removeFromFavourite(vacancyId: String) {
        vacanciesDao.removeFromFavourite(vacancyId)
    }
}