package com.example.petadoptionapp.repository.local_data_source

import com.example.petadoptionapp.network.models.Hour
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HourCache @Inject constructor() {

    private val cache: MutableStateFlow<List<Hour>> = MutableStateFlow(mutableListOf())
    private val clickedHour: MutableStateFlow<Hour?> = MutableStateFlow(null)
    private val endClickedHour: MutableStateFlow<Hour?> = MutableStateFlow(null)

    fun saveZonesAndDurations(data: List<Hour>) {
        cache.value = data
    }

    fun hourObservable(): Flow<List<Hour>> {
        return cache.asStateFlow()
    }

    fun setClickedHour(hour: Hour?) {
        clickedHour.value = hour
    }

    fun getClickedHourObservable(): Flow<Hour?> {
        return clickedHour
    }

    fun endHourObservable(): Flow<List<Hour>> {
        return cache.asStateFlow()
    }

    fun setClickedEndHour(hour: Hour?) {
        endClickedHour.value = hour
    }

    fun getClickedEndHourObservable(): Flow<Hour?> {
        return endClickedHour
    }

    fun clearCache() {
        cache.value = mutableListOf()
        clickedHour.value = null
        endClickedHour.value = null
    }
}