package com.example.petadoptionapp.repository.animals_repository

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.network.models.response.NPostAnimalResponse
import com.example.petadoptionapp.network.models.response.NUploadAsset
import com.example.petadoptionapp.network.services.animals.AnimalsApiInterface
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.repository.mapper.responses.NAnimalResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

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
//        delay(2.seconds)
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.getAllAnimals().results.map { NAnimalResponseMapper().map(it) }
//                getAnimalsDummy()
            }
        }
    }

    override suspend fun getAnimalsBySpecie(specie: String): Result<List<AnimalResponse>> {
//        delay(2.seconds)
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.getAnimalsBySpecie(specie).results.map {
                    NAnimalResponseMapper().map(it)
                }
            }
        }
    }

    override suspend fun getAnimalsByAdoptionCenterId(adoptionCenterId: String): Result<List<AnimalResponse>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.getAnimalsByAdoptionCenterId(adoptionCenterId).results.map {
                    NAnimalResponseMapper().map(it)
                }
            }
        }
    }

    override suspend fun addAnimal(data: NAnimalParam): Result<NPostAnimalResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.addAnimal(data)
            }
        }
    }

    override suspend fun uploadImage(id: String, image: File): Result<NUploadAsset> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.uploadAnimalImage(id = id, image = image)
            }
        }
    }

    override suspend fun editAnimal(id: String, data: NAnimalParam): Result<NPostAnimalResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.editAnimal(id = id, data = data)
            }
        }
    }

    override suspend fun deleteAnimal(id: String): Result<Any> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAnimalsInterface.deleteAnimal(id = id)
            }
        }
    }

    fun getAnimalsDummy(): List<AnimalResponse> {
        val list = mutableListOf<AnimalResponse>()
//        val a1 = AnimalResponse(
//            "1",
//            EPetCategory.DOG,
//            EPetGender.MALE,
//            name = "Luciano",
//            breed = "Golden Retriever",
//            age = 12,
//            vaccinated = true,
//            neutered = false,
//            story = "Golden Retriever - a ray of sunshine in the form of a dog! Friendly, affectionate, and intelligent, this golden companion will bring joy and love into your life. With their shiny coat and cheerful personality, Golden Retrievers are perfect for loving families. Adopt a Golden Retriever and you'll have a loyal and understanding friend for life.",
//            imageUrl = "https://images.unsplash.com/photo-1554456854-55a089fd4cb2?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a2 = AnimalResponse(
//            "2",
//            EPetCategory.DOG,
//            EPetGender.FEMALE,
//            name = "Bella",
//            breed = "Corgi",
//            age = 24,
//            vaccinated = true,
//            neutered = true,
//            story = "Corgis are loving, loyal, and extremely friendly, often referred to as \"little royals\" due to their regal and charismatic demeanor. With their perpetual smile and wagging tail, corgis have the ability to melt hearts with their charm. Whether they're seeking adventures or learning new tricks, corgis are always ready to embrace life and provide unconditional love.",
//            imageUrl = "https://images.unsplash.com/photo-1554693190-383dd5302125?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=871&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a3 = AnimalResponse(
//            "3",
//            EPetCategory.BIRD,
//            EPetGender.FEMALE,
//            name = "Henrietta",
//            breed = "Common breed",
//            age = 2,
//            vaccinated = false,
//            neutered = false,
//            story = "Henrietta is a chicken with shiny golden feathers and a wise eye. She has been through a lot in her life, and now it's time for her to find a family who will welcome her with open arms.",
//            imageUrl = "https://images.unsplash.com/photo-1580866177074-5c0b1b924d32?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a4 = AnimalResponse(
//            "4",
//            EPetCategory.CAT,
//            EPetGender.FEMALE,
//            name = "Luna",
//            breed = "Common breed",
//            age = 2,
//            vaccinated = false,
//            neutered = true,
//            story = "This is the story of an adorable cat named Luna who is eagerly seeking a loving home. Luna is a fluffy cat with bright eyes, bringing with her a special charm",
//            imageUrl = "https://images.unsplash.com/photo-1518791841217-8f162f1e1131?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a5 = AnimalResponse(
//            "4",
//            EPetCategory.CAT,
//            EPetGender.MALE,
//            name = "Oliver",
//            breed = "Common breed",
//            age = 2,
//            vaccinated = true,
//            neutered = false,
//            story = "Oliver was a mischievous and playful cat, always finding himself in the most adventurous situations. He loved exploring every nook and cranny, from rooftops to hidden garden corners. ",
//            imageUrl = "https://images.unsplash.com/photo-1472491235688-bdc81a63246e?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        val a6 = AnimalResponse(
//            "4",
//            EPetCategory.BIRD,
//            EPetGender.MALE,
//            name = "Max",
//            breed = "Common breed",
//            age = 1,
//            vaccinated = false,
//            neutered = false,
//            story = "Max had a colorful plumage, with vibrant shades of red, gold, and green that glistened in the sunlight. He was a rooster with a strong and powerful voice, announcing each sunrise with a joyful crow.",
//            imageUrl = "https://images.unsplash.com/photo-1579088013384-34f5f99cacdb?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=870&q=80",
//            adoptionCenterId = "81925700-13af-11ee-85fb-a932987c84ca",
//            createdAt = "2023-06-25T23:25:00.912Z",
//            updatedAt = "2023-06-25T23:25:00.912Z"
//        )
//
//        list.add(a1)
//        list.add(a2)
//        list.add(a3)
//        list.add(a4)
//        list.add(a5)
//        list.add(a6)

        return list

    }
}