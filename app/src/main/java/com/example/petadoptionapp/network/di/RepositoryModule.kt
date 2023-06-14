package com.example.petadoptionapp.network.di

import com.example.petadoptionapp.repository.favorites_repository.FavoritesRepository
import com.example.petadoptionapp.repository.local_data_source.SharedPreferencesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideFavoritesRepository(
        sharedPreferencesDao: SharedPreferencesDao,
        json: Json,
    ): FavoritesRepository {
        return FavoritesRepository(json, sharedPreferencesDao)
    }
}