package com.example.feature_main.presenation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_main.domain.entity.Vacancy
import com.example.feature_main.domain.usecase.GetVacanciesUseCase
import com.example.feature_main.domain.usecase.ObserveFavouriteVacancyUseCase
import com.example.feature_main.domain.usecase.ObserveVacanciesUseCase
import com.example.feature_main.domain.usecase.ToggleFavoriteVacancyUseCase
import com.example.feature_main.domain.usecase.UpdateVacanciesUseCase
import com.example.feature_main.presenation.main.MainUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase,
    private val toggleFavoriteVacancyUseCase: ToggleFavoriteVacancyUseCase,
    private val observeFavouriteVacancyUseCase: ObserveFavouriteVacancyUseCase,
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
            getVacanciesUseCase()
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

    private suspend fun updateVacancyFavorites() {
        val updatedList = (_state.value as MainUiState.Success).data.vacancies.map { vacancy ->
            vacancy.copy(isFavorite = observeFavouriteVacancyUseCase(vacancy.id).first())
        }
        _state.value =
            MainUiState.Success((_state.value as MainUiState.Success).data.copy(vacancies = updatedList))
        Log.i("MyTag", state.value.toString())
    }

//    private fun getVacancies() {
//        viewModelScope.launch {
//            _state.value = MainUiState.Loading
//
//            when (val data = getVacanciesUseCase()) {
//                is ApiResponse.Success -> _state.value = MainUiState.Success(data.data)
//                is ApiResponse.Error -> _state.value = MainUiState.Error(data.message)
//            }
//        }
//    }
}