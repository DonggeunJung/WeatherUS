package com.example.weatherus.di

import android.content.Context
import com.example.weatherus.data.StateApi
import com.example.weatherus.data.StateDatabase
import com.example.weatherus.data.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideStateDatabase(@ApplicationContext context: Context): StateDatabase {
        return StateDatabase.getInstance(context)
    }

    @Singleton
    @Provides
    fun provideStateApi(): StateApi {
        // Create Retrofit API object & return
        return StateApi.instance
    }

    @Singleton
    @Provides
    fun provideWeatherApi(): WeatherApi {
        // Create Retrofit API object & return
        return WeatherApi.instance
    }

}