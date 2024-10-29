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

    private val _vacanciesFlow = MutableStateFlow(
        Response(emptyList(), emptyList())
    )
    override val vacanciesFlow = _vacanciesFlow.asStateFlow()

    override suspend fun getMainFeatureData() {
        val response = apiService.getMockData().toEntity()

        checkLaunchApp(response)
        _vacanciesFlow.emit(getMappedResponse(response))
    }

    override fun observeIsFavourite(id: String): Flow<Boolean> {
        return vacanciesDao.observeIsFavourite(id)
    }

    override suspend fun addVacancyToFavourite(vacancy: Vacancy) {
        vacanciesDao.addToFavouriteVacancy(vacancy.toVacancyDb())
    }

    override suspend fun removeVacancyFromFavourite(id: String) {
        vacanciesDao.removeFromFavourite(id)
    }

    override suspend fun updateVacancies() {
        _vacanciesFlow.emit(getUpdatedVacancies())
    }

    private suspend fun getMappedResponse(response: Response): Response {
        return response.copy(vacancies = response.vacancies.map { vacancy ->
            val isFavourite = observeIsFavourite(vacancy.id).first()
            vacancy.copy(isFavorite = isFavourite)
        })
    }

    private suspend fun checkLaunchApp(response: Response) {
        val isFirstLaunch = sharedPreferences.getBoolean(IS_FIRST_LAUNCH, true)

        if (isFirstLaunch) {
            response.vacancies
                .filter { it.isFavorite }
                .forEach { addVacancyToFavourite(it) }

            sharedPreferences.edit().putBoolean(IS_FIRST_LAUNCH, false).apply()
        }
    }

    private suspend fun getUpdatedVacancies(): Response {
        return _vacanciesFlow.updateAndGet {
            it.copy(vacancies = it.vacancies.map { vacancy ->
                vacancy.copy(isFavorite = observeIsFavourite(vacancy.id).first())
            })
        }
    }

    companion object {
        const val IS_FIRST_LAUNCH = "is_first_launch"
    }
}

