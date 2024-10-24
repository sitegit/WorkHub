package com.example.feature_main.di

import com.example.feature_main.data.MainFeatureRepositoryImpl
import com.example.feature_main.domain.MainFeatureRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface MainModule {

    @Binds
    @Singleton
    fun bindMainFeatureRepository(impl: MainFeatureRepositoryImpl): MainFeatureRepository
}