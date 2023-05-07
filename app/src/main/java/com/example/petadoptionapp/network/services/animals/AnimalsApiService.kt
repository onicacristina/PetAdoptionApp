package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.AnimalResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimalsApiService {
    @GET("animals/{id}")
    fun getOneAnimalById(@Path("id") id: String): Call<AnimalResponse>

    @GET("animals")
    fun getAllAnimals(): Call<List<AnimalResponse>>

    @GET("animals")
    fun getAnimalsBySpecie(@Query("specie") specie: String): Call<List<AnimalResponse>>
}