package com.example.petadoptionapp.repository.pet_specie_repository

import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import kotlinx.coroutines.flow.Flow


interface PetSpecieRepositoryInterface {
    suspend fun getSpecieList(): Result<List<EPetCategory>>

    fun specieObservable(): Flow<List<EPetCategory>>
    fun clickedSpecieObservable(): Flow<EPetCategory?>
    fun setClickedSpecie(specie: EPetCategory?)

    fun clearCache()
}