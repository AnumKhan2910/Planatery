package com.adyen.android.assignment.di

import com.adyen.android.assignment.domain.*
import dagger.*
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class PlanetaryDIModule {

    @Provides
    fun providesShiftsService(retrofit: Retrofit): PlanetaryService =
        retrofit.create(PlanetaryService::class.java)
}

@Module
@InstallIn(ViewModelComponent::class)
interface PlanetaryRepositoryModule {

    @Binds
    fun bindPlanetaryRepository(defaultPlanetaryRepository: DefaultPlanetaryRepository): PlanetaryRepository

    @Binds
    fun bindAPODUseCase(getAPODUseCase: DefaultGetAPODUseCase): GetAPODUseCase

}