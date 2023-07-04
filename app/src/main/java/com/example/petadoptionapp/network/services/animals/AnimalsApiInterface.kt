package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.*
import okhttp3.RequestBody

interface AnimalsApiInterface {
    suspend fun getOneAnimalById(id: String): NAnimalResponse
    suspend fun getAllAnimals(): NAnimalsListResponse
    suspend fun getAnimalsBySpecie(specie: String): NAnimalsListResponse
    suspend fun getAnimalsByAdoptionCenterId(adoptionCenterId: String): NAnimalsListResponse

    suspend fun addAnimal(data: NAnimalParam): NPostAnimalResponse
    suspend fun uploadAnimalImage(id: String, image: RequestBody): NUploadAsset
    suspend fun editAnimal(id: String, data: NAnimalParam): NPostAnimalResponse
    suspend fun deleteAnimal(id: String): NMessageResponse
}