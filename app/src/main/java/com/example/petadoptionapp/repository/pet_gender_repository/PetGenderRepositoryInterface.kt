package com.example.petadoptionapp.repository.pet_gender_repository

import com.example.petadoptionapp.presentation.ui.home.EPetGender
import kotlinx.coroutines.flow.Flow

interface PetGenderRepositoryInterface {
    suspend fun getGenderList(): Result<List<EPetGender>>

    fun genderObservable(): Flow<List<EPetGender>>
    fun clickedGenderObservable(): Flow<EPetGender?>
    fun setClickedGender(gender: EPetGender?)

    fun clearCache()
}