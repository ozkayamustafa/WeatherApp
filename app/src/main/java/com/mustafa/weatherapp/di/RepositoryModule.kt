package com.mustafa.weatherapp.di

import com.mustafa.weatherapp.data.repository.WeatherRepositoryIml
import com.mustafa.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract  fun bindWeatherRepository(repo:WeatherRepositoryIml):WeatherRepository

}