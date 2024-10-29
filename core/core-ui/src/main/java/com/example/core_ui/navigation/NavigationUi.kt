package com.example.core_ui.navigation

interface NavigationUi {

    fun navigateToRelevantVacanciesFragment()

    fun navigateFromRelevantToDetailFragment()

    fun navigateFromMainToDetailFragment()

    fun navigateFromFavouriteToDetailFragment()

    fun popUp()
}