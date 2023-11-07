package com.example.petadoptionapp.repository.local_data_source

import com.example.petadoptionapp.presentation.ui.home.EPetGender
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GenderCache @Inject constructor() {

    private val cache: MutableStateFlow<List<EPetGender>> = MutableStateFlow(mutableListOf())
    private val clickedGender: MutableStateFlow<EPetGender?> = MutableStateFlow(null)

    fun saveGenders(data: List<EPetGender>) {
        cache.value = data
    }

    fun genderObservable(): Flow<List<EPetGender>> {
        return cache.asStateFlow()
    }

    fun setClickedGender(gender: EPetGender?) {
        clickedGender.value = gender
    }

    fun getClickedGenderObservable(): Flow<EPetGender?> {
        return clickedGender
    }

    fun clearCache() {
        cache.value = mutableListOf()
        clickedGender.value = null
    }
}