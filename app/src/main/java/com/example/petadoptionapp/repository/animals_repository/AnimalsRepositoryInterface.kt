package com.example.petadoptionapp.repository.animals_repository

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.network.models.response.NPostAnimalResponse
import com.example.petadoptionapp.network.models.response.NUploadAsset
import okhttp3.MultipartBody
import okhttp3.RequestBody

interface AnimalsRepositoryInterface {
    suspend fun getOneAnimalById(id: String): Result<AnimalResponse>
    suspend fun getAllAnimals(): Result<List<AnimalResponse>>
    suspend fun getAnimalsBySpecie(specie: String): Result<List<AnimalResponse>>
    suspend fun getAnimalsByAdoptionCenterId(adoptionCenterId: String): Result<List<AnimalResponse>>

    suspend fun addAnimal(data: NAnimalParam): Result<NPostAnimalResponse>
    suspend fun uploadImage(id: String, image: RequestBody): Result<NUploadAsset>
    suspend fun editAnimal(id: String, data: NAnimalParam): Result<NPostAnimalResponse>
    suspend fun deleteAnimal(id: String): Result<Any>
}