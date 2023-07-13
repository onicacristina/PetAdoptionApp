package com.example.petadoptionapp.repository.hour_repository

import com.example.petadoptionapp.network.models.Hour
import kotlinx.coroutines.flow.Flow

interface HourRepositoryInterface {
    suspend fun getHoursList(): Result<List<Hour>>

    fun hourObservable(): Flow<List<Hour>>
    fun clickedHourObservable(): Flow<Hour?>
    fun setClickedHour(hour: Hour?)

    fun endHourObservable(): Flow<List<Hour>>
    fun clickedEndHourObservable(): Flow<Hour?>
    fun setClickedEndHour(hour: Hour?)

    fun clearCache()
}