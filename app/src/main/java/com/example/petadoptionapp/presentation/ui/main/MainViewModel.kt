package com.example.petadoptionapp.presentation.ui.main

import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.repository.hour_repository.HourRepository
import com.example.petadoptionapp.repository.local_data_source.SpecieCache
import com.example.petadoptionapp.repository.pet_gender_repository.PetGenderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val genderRepository: PetGenderRepository,
    private val hourRepository: HourRepository,
    private val specieCache: SpecieCache
) : BaseViewModel() {

    fun clearCaches() {
        genderRepository.clearCache()
        hourRepository.clearCache()
        specieCache.clearCache()
    }
}