package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.response.AnimalResponse

interface AnimalsRepositoryInterface {
    suspend fun getOneAnimalById(id: String): Result<AnimalResponse>
    suspend fun getAllAnimals(): Result<List<AnimalResponse>>
    suspend fun getAnimalsBySpecie(specie: String): Result<List<AnimalResponse>>
}