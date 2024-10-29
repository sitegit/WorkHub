package com.example.feature_main.presenation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_main.domain.ApiResponse
import com.example.feature_main.domain.entity.Vacancy
import com.example.feature_main.domain.usecase.GetVacanciesUseCase
import com.example.feature_main.domain.usecase.ObserveVacanciesUseCase
import com.example.feature_main.domain.usecase.ToggleFavoriteVacancyUseCase
import com.example.feature_main.domain.usecase.UpdateVacanciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val toggleFavoriteVacancyUseCase: ToggleFavoriteVacancyUseCase,
    private val observeVacanciesUseCase: ObserveVacanciesUseCase,
    private val updateVacanciesUseCase: UpdateVacanciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.Initial)
    val state = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            _state.value = MainUiState.Loading
            getInitialData()
            observeVacanciesUseCase().collect { response ->
                _state.value = MainUiState.Success(response)
            }
        }
    }

    fun toggleFavorite(vacancy: Vacancy) {
        viewModelScope.launch {
            toggleFavoriteVacancyUseCase(vacancy)
        }
    }

    fun updateVacancies() {
        viewModelScope.launch {
            updateVacanciesUseCase()
        }
    }

    private suspend fun getInitialData() {
        val vacancies = getVacanciesUseCase()
        if (vacancies is ApiResponse.Error) {
            _state.value = MainUiState.Error(vacancies.message)
        }
    }
}