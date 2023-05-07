package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.AnimalResponse
import retrofit2.Call

interface AnimalsApiInterface {
    suspend fun getOneAnimalById(id: String): Call<AnimalResponse>
    suspend fun getAllAnimals(): Call<List<AnimalResponse>>
    suspend fun getAnimalsBySpecie(specie: String): Call<List<AnimalResponse>>
}