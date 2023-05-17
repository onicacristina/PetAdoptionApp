package com.example.petadoptionapp.repository.animals_repository

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.network.services.animals.AnimalsApiInterface
import com.example.petadoptionapp.repository.mapper.responses.NAnimalResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class AnimalsRepository @Inject constructor(
    private val apiAnimalsInterface: AnimalsApiInterface
) : AnimalsRepositoryInterface {
    override suspend fun getOneAnimalById(id: String): Result<AnimalResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val result = apiAnimalsInterface.getOneAnimalById(id)
                NAnimalResponseMapper().map(result)
            }
        }
    }

    override suspend fun getAllAnimals(): Result<List<AnimalResponse>> {
        delay(2.seconds)
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.getAllAnimals().results.map { NAnimalResponseMapper().map(it) }
            }
        }
    }

    override suspend fun getAnimalsBySpecie(specie: String): Result<List<AnimalResponse>> {
//        delay(2.seconds)
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.getAnimalsBySpecie(specie).results.map {
                    NAnimalResponseMapper().map(
                        it
                    )
                }
            }
        }
    }
}