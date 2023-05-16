package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse

interface AnimalsApiInterface {
    suspend fun getOneAnimalById(id: String): NAnimalResponse
    suspend fun getAllAnimals(): NAnimalsListResponse
    suspend fun getAnimalsBySpecie(specie: String): NAnimalsListResponse
}