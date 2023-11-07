package com.example.petadoptionapp.repository.pet_gender_repository

import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.repository.local_data_source.GenderCache
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PetGenderRepository @Inject constructor(
    private val cache: GenderCache
) : PetGenderRepositoryInterface {

    init {
        cache.saveGenders(getDummyData())
    }

    override suspend fun getGenderList(): Result<List<EPetGender>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                getDummyData()
            }.onSuccess {
                cache.saveGenders(it)
            }
        }
    }

    override fun genderObservable(): Flow<List<EPetGender>> {
        return cache.genderObservable()
    }

    override fun clickedGenderObservable(): Flow<EPetGender?> {
        return cache.getClickedGenderObservable()
    }

    override fun setClickedGender(gender: EPetGender?) {
        cache.setClickedGender(gender)
    }

    override fun clearCache() {
        cache.clearCache()
    }

    private fun getDummyData(): List<EPetGender> {
        val dummyData = mutableListOf<EPetGender>()

        dummyData.add(EPetGender.MALE)
        dummyData.add(EPetGender.FEMALE)

        return dummyData
    }
}