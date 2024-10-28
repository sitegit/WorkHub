package com.example.workhub.di

import android.content.Context
import com.example.core_db.di.DbModule
import com.example.core_network.di.NetworkModule
import com.example.feature_favourite.di.FavouriteComponentDependencies
import com.example.feature_favourite.di.FavouriteModule
import com.example.feature_main.di.MainComponentDependencies
import com.example.feature_main.di.MainModule
import com.example.workhub.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        DbModule::class,
        FavouriteModule::class,
        MainModule::class
    ]
)
interface ApplicationComponent : MainComponentDependencies, FavouriteComponentDependencies {

    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context
        ): ApplicationComponent
    }
}