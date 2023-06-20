package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAnimalResponse

interface AnimalsApiInterface {
    suspend fun getOneAnimalById(id: String): NAnimalResponse
    suspend fun getAllAnimals(): NAnimalsListResponse
    suspend fun getAnimalsBySpecie(specie: String): NAnimalsListResponse
    suspend fun getAnimalsByAdoptionCenterId(adoptionCenterId: String): NAnimalsListResponse

    suspend fun addAnimal(data: NAnimalParam): NPostAnimalResponse
    suspend fun editAnimal(id: String, data: NAnimalParam): NPostAnimalResponse

    suspend fun deleteAnimal(id: String): NMessageResponse
}