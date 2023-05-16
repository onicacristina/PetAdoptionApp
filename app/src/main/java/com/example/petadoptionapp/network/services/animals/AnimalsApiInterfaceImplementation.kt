package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import javax.inject.Inject

class AnimalsApiInterfaceImplementation @Inject constructor(
    private val animalsApiService: AnimalsApiService
) : AnimalsApiInterface {
    override suspend fun getOneAnimalById(id: String): NAnimalResponse {
        return animalsApiService.getOneAnimalById(id)
    }

    override suspend fun getAllAnimals(): NAnimalsListResponse {
        return animalsApiService.getAllAnimals()
    }

    override suspend fun getAnimalsBySpecie(specie: String): NAnimalsListResponse {
        return animalsApiService.getAnimalsBySpecie(specie)
    }
}