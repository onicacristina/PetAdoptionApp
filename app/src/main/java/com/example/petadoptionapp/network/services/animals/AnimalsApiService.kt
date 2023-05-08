package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface AnimalsApiService {
    @GET("animals/{id}")
    fun getOneAnimalById(@Path("id") id: String): Call<NAnimalResponse>

    @GET("animals")
    fun getAllAnimals(): Call<NAnimalsListResponse>

    @GET("animals")
    fun getAnimalsBySpecie(@Query("specie") specie: String): Call<NAnimalsListResponse>
}