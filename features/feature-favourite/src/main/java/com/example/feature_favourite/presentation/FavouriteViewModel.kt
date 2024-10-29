package com.example.feature_favourite.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_favourite.domain.DbResponse
import com.example.feature_favourite.domain.GetVacanciesUseCase
import com.example.feature_favourite.domain.RemoveFavoriteVacancyUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val removeFavoriteVacancyUseCase: RemoveFavoriteVacancyUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FavouriteUiState>(FavouriteUiState.Initial)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.value = FavouriteUiState.Loading
            getVacanciesUseCase().collect { vacancies ->
                when (vacancies) {
                    is DbResponse.Error -> _state.value = FavouriteUiState.Error(vacancies.message)
                    is DbResponse.Success -> _state.value = FavouriteUiState.Success(vacancies.data)
                }
            }
        }
    }

    fun removeVacancy(vacancyId: String) {
        viewModelScope.launch {
            removeFavoriteVacancyUseCase(vacancyId)
        }
    }
}