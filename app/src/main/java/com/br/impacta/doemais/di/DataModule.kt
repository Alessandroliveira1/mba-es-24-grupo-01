package com.br.impacta.doemais.di


import com.br.impacta.doemais.data.location.ILocationRepository
import com.br.impacta.doemais.data.location.LocationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Singleton
    @Binds
    abstract fun bindLocationRepository(
        locationRepository: LocationRepository
    ): ILocationRepository




}