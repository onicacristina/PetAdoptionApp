package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import retrofit2.Call

interface AnimalsApiInterface {
    suspend fun getOneAnimalById(id: String): Call<NAnimalResponse>
    suspend fun getAllAnimals(): Call<NAnimalsListResponse>
    suspend fun getAnimalsBySpecie(specie: String): Call<NAnimalsListResponse>
}