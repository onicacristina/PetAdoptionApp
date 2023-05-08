package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.network.services.animals.AnimalsApiInterface
import com.example.petadoptionapp.repository.extensions.Result
import com.example.petadoptionapp.repository.extensions.map
import com.example.petadoptionapp.repository.extensions.parseResponse
import com.example.petadoptionapp.repository.mapper.responses.NAnimalResponseMapper
import javax.inject.Inject

class AnimalsRepository @Inject constructor(
    private val apiAnimalsInterface: AnimalsApiInterface
) : AnimalsRepositoryInterface{
    override suspend fun getOneAnimalById(id: String): Result<Failure, AnimalResponse> {
        val result = apiAnimalsInterface.getOneAnimalById(id)
        val parsedResponse = result.parseResponse()
        return parsedResponse.map { NAnimalResponseMapper().map(it) }
    }

    override suspend fun getAllAnimals(): Result<Failure, List<AnimalResponse>> {
        val result = apiAnimalsInterface.getAllAnimals()
        val parsedResponse = result.parseResponse()
        return parsedResponse.map { animal -> animal.results.map { NAnimalResponseMapper().map(it) } }
    }

    override suspend fun getAnimalsBySpecie(specie: String): Result<Failure, List<AnimalResponse>> {
        val result = apiAnimalsInterface.getAnimalsBySpecie(specie)
        val parsedResponse = result.parseResponse()
        return parsedResponse.map { animal -> animal.results.map { NAnimalResponseMapper().map(it) } }
    }
}