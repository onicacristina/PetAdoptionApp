package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.AnimalResponse
import retrofit2.Call
import javax.inject.Inject

class AnimalsApiInterfaceImplementation  @Inject constructor(
    private val animalsApiService: AnimalsApiService
): AnimalsApiInterface{
    override suspend fun getOneAnimalById(id: String): Call<AnimalResponse> {
        return animalsApiService.getOneAnimalById(id)
    }

    override suspend fun getAllAnimals(): Call<List<AnimalResponse>> {
        return animalsApiService.getAllAnimals()
    }

    override suspend fun getAnimalsBySpecie(specie: String): Call<List<AnimalResponse>> {
        return animalsApiService.getAnimalsBySpecie(specie)
    }
}