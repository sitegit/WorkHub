package com.example.feature_main.di

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.core_ui.di.ViewModelKey
import com.example.feature_main.data.MainRepositoryImpl
import com.example.feature_main.domain.MainRepository
import com.example.feature_main.presenation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface MainModule {

    @Binds
    @Singleton
    fun bindMainFeatureRepository(impl: MainRepositoryImpl): MainRepository

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    companion object {

        @Provides
        @Singleton
        fun provideSharedPreferences(context: Context): SharedPreferences {
            return context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
        }
    }
}