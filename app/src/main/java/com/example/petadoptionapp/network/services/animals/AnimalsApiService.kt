package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimalsApiService {
    @GET("animals/{id}")
    suspend fun getOneAnimalById(@Path("id") id: String): NAnimalResponse

    @GET("animals")
    suspend fun getAllAnimals(): NAnimalsListResponse

    @GET("animals")
    suspend fun getAnimalsBySpecie(@Query("specie") specie: String): NAnimalsListResponse
}