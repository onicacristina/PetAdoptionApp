package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.repository.extensions.Result

interface AnimalsRepositoryInterface {
    suspend fun getOneAnimalById(id: String): Result<Failure, AnimalResponse>
    suspend fun getAllAnimals(): Result<Failure, List<AnimalResponse>>
    suspend fun getAnimalsBySpecie(specie: String): Result<Failure, List<AnimalResponse>>
}