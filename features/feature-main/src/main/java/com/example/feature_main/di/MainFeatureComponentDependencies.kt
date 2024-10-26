package com.example.feature_main.di

import android.content.Context
import com.example.core_ui.utils.ViewModelFactory
import com.example.feature_main.domain.MainFeatureRepository

interface MainFeatureComponentDependencies {

    fun getMainFeatureRepository(): MainFeatureRepository

    fun getViewModelFactory(): ViewModelFactory

    fun getContext(): Context
}