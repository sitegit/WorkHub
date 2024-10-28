package com.example.feature_favourite.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                Log.i("MyTag", vacancies.map { it.isFavorite }.toString())
                _state.value = FavouriteUiState.Success(vacancies)
            }
        }
    }

    fun removeVacancy(vacancyId: String) {
        viewModelScope.launch {
            removeFavoriteVacancyUseCase(vacancyId)
        }
    }
}