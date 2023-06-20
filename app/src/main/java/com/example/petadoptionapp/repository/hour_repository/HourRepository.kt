package com.example.petadoptionapp.repository.hour_repository

import com.example.petadoptionapp.network.models.Hour
import com.example.petadoptionapp.repository.local_data_source.HourCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HourRepository @Inject constructor(
    private val cache: HourCache
) : HourRepositoryInterface {

    init {
        cache.saveZonesAndDurations(getDummyData())
    }

    override suspend fun getHoursList(): Result<List<Hour>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                getDummyData()
            }.onSuccess {
                cache.saveZonesAndDurations(it)
            }
        }
    }

    override fun hourObservable(): Flow<List<Hour>> {
        return cache.hourObservable()
    }

    override fun clickedHourObservable(): Flow<Hour?> {
        return cache.getClickedHourObservable()
    }

    override fun setClickedHour(hour: Hour?) {
        cache.setClickedHour(hour)
    }

    override fun endHourObservable(): Flow<List<Hour>> {
        return cache.endHourObservable()
    }

    override fun clickedEndHourObservable(): Flow<Hour?> {
        return cache.getClickedEndHourObservable()
    }

    override fun setClickedEndHour(hour: Hour?) {
        cache.setClickedEndHour(hour)
    }

    override fun clearCache() {
        cache.clearCache()
    }

//    private fun getDummyData(): List<Hour> {
//        val dummyData = mutableListOf<Hour>()
//
//        for (hour in 8..18) {
//            val hourString = String.format("%02d:00", hour)
//            val hourItem = Hour(hour, hourString)
//            dummyData.add(hourItem)
//        }
//
//        return dummyData
//    }

    private fun getDummyData(): List<Hour> {
        val dummyData = mutableListOf<Hour>()

        for (hour in 8..17) { // Reduce range to 17 instead of 18 since we are adding minutes
            val hourString = String.format("%02d:00", hour)
            val hourItem = Hour(hour, hourString)
            dummyData.add(hourItem)

            val halfHourString = String.format("%02d:30", hour)
            val halfHourItem = Hour(hour, halfHourString)
            dummyData.add(halfHourItem)
        }

        return dummyData
    }

}