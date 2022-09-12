package com.mustafa.weatherapp.di

import com.mustafa.weatherapp.data.location.DefaultLocationTracker
import com.mustafa.weatherapp.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(lcoation:DefaultLocationTracker):LocationTracker

}