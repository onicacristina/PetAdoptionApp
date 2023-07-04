package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.*
import okhttp3.RequestBody
import javax.inject.Inject

class AnimalsApiInterfaceImplementation @Inject constructor(
    private val animalsApiService: AnimalsApiService
) : AnimalsApiInterface {
    override suspend fun getOneAnimalById(id: String): NAnimalResponse {
        return animalsApiService.getOneAnimalById(id)
    }

    override suspend fun getAllAnimals(): NAnimalsListResponse {
        return animalsApiService.getAllAnimals()
    }

    override suspend fun getAnimalsBySpecie(specie: String): NAnimalsListResponse {
        return animalsApiService.getAnimalsBySpecie(specie)
    }

    override suspend fun getAnimalsByAdoptionCenterId(adoptionCenterId: String): NAnimalsListResponse {
        return animalsApiService.getAnimalsByAdoptionCenterId(adoptionCenterId)
    }

    override suspend fun addAnimal(data: NAnimalParam): NPostAnimalResponse {
        return animalsApiService.addAnimal(data)
    }

    override suspend fun uploadAnimalImage(id: String, image: RequestBody): NUploadAsset {
        return animalsApiService.uploadAnimalImage(id = id, imageData = image)
    }

    override suspend fun editAnimal(id: String, data: NAnimalParam): NPostAnimalResponse {
        return animalsApiService.editAnimal(id, data)
    }

    override suspend fun deleteAnimal(id: String): NMessageResponse {
        return animalsApiService.deleteAnimal(id)
    }
}