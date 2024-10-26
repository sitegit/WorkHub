package com.example.feature_main.presenation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_main.domain.ApiResponse
import com.example.feature_main.domain.entity.Vacancy
import com.example.feature_main.domain.usecase.GetVacanciesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getVacanciesUseCase: GetVacanciesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<MainUiState>(MainUiState.Initial)
    val state = _state.asStateFlow()

    init {
        getVacancies()
    }

    fun onFavoriteClick(vacancy: Vacancy) {

    }

    private fun getVacancies() {
        viewModelScope.launch {
            _state.value = MainUiState.Loading

            when (val data = getVacanciesUseCase()) {
                is ApiResponse.Success -> _state.value = MainUiState.Success(data.data)
                is ApiResponse.Error -> _state.value = MainUiState.Error(data.message)
            }
        }
    }
}