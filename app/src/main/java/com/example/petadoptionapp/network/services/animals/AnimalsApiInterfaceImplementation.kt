package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import retrofit2.Call
import javax.inject.Inject

class AnimalsApiInterfaceImplementation @Inject constructor(
    private val animalsApiService: AnimalsApiService
) : AnimalsApiInterface {
    override suspend fun getOneAnimalById(id: String): Call<NAnimalResponse> {
        return animalsApiService.getOneAnimalById(id)
    }

    override suspend fun getAllAnimals(): Call<NAnimalsListResponse> {
        return animalsApiService.getAllAnimals()
    }

    override suspend fun getAnimalsBySpecie(specie: String): Call<NAnimalsListResponse> {
        return animalsApiService.getAnimalsBySpecie(specie)
    }
}