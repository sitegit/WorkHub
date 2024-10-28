package com.example.feature_main.data

import android.content.SharedPreferences
import com.example.core_db.FavouriteVacanciesDao
import com.example.core_network.ApiService
import com.example.feature_main.domain.MainRepository
import com.example.feature_main.domain.entity.Response
import com.example.feature_main.domain.entity.Vacancy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.updateAndGet
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val vacanciesDao: FavouriteVacanciesDao,
    private val sharedPreferences: SharedPreferences
) : MainRepository{

    private val _vacanciesFlow = MutableStateFlow(Response(emptyList(), emptyList()))
    override val vacanciesFlow = _vacanciesFlow.asStateFlow()

    override suspend fun getMainFeatureData() {
        val isFirstLaunch = sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)
        var response = apiService.getMockData().toEntity()

        if (isFirstLaunch) {
            addFavouriteVacanciesToDb(response.vacancies)
            sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
        }

        response = response.copy(vacancies = response.vacancies.map { vacancy ->
            val isFavourite = observeIsFavourite(vacancy.id).first()
            vacancy.copy(isFavorite = isFavourite)
        })

        _vacanciesFlow.emit(response)
    }

    override fun observeIsFavourite(id: String): Flow<Boolean> {
        return vacanciesDao.observeIsFavourite(id)
    }

    override suspend fun toggleFavoriteVacancy(vacancy: Vacancy) {
        val isFavorite = observeIsFavourite(vacancy.id).first()

        if (!isFavorite) {
            vacanciesDao.addToFavouriteVacancy(vacancy.copy(isFavorite = true).toVacancyDb())
        } else {
            vacanciesDao.removeFromFavourite(vacancy.id)
        }

        updateVacancies()
    }

    override suspend fun updateVacancies() {
        _vacanciesFlow.emit(getUpdatedVacancies())
    }

    private suspend fun getUpdatedVacancies(): Response {
        return _vacanciesFlow.updateAndGet {
            it.copy(vacancies = it.vacancies.map { vacancy ->
                vacancy.copy(isFavorite = observeIsFavourite(vacancy.id).first())
            })
        }
    }

    private suspend fun addFavouriteVacanciesToDb(vacancies: List<Vacancy>) {
        vacancies
            .filter { it.isFavorite }
            .forEach { vacanciesDao.addToFavouriteVacancy(it.toVacancyDb()) }
    }

    companion object {
        const val IS_FIRST_LAUNCH = "is_first_launch"
    }
}

