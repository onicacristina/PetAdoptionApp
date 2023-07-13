package com.example.petadoptionapp.repository.local_data_source

import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SpecieCache @Inject constructor() {

    private val cache: MutableStateFlow<List<EPetCategory>> = MutableStateFlow(mutableListOf())
    private val clickedSpecie: MutableStateFlow<EPetCategory?> = MutableStateFlow(null)

    fun saveSpecies(data: List<EPetCategory>) {
        cache.value = data
    }

    fun specieObservable(): Flow<List<EPetCategory>> {
        return cache.asStateFlow()
    }

    fun setClickedSpecie(specie: EPetCategory?) {
        clickedSpecie.value = specie
    }

    fun getClickedSpecieObservable(): Flow<EPetCategory?> {
        return clickedSpecie
    }

    fun clearCache() {
        cache.value = mutableListOf()
        clickedSpecie.value = null
    }
}