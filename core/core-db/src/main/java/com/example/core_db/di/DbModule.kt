package com.example.core_db.di

import android.content.Context
import com.example.core_db.AppDatabase
import com.example.core_db.FavouriteVacanciesDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase = AppDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideFavouriteVacanciesDao(database: AppDatabase): FavouriteVacanciesDao =
        database.favouriteVacanciesDao()
}