package com.example.petadoptionapp.network.services.animals

import com.example.petadoptionapp.network.models.request.NAnimalParam
import com.example.petadoptionapp.network.models.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface AnimalsApiService {
    @GET("api/animals/{id}")
    suspend fun getOneAnimalById(@Path("id") id: String): NAnimalResponse

    @GET("api/animals")
    suspend fun getAllAnimals(): NAnimalsListResponse

    @GET("api/animals")
    suspend fun getAnimalsBySpecie(@Query("specie") specie: String): NAnimalsListResponse

    @GET("api/animals")
    suspend fun getAnimalsByAdoptionCenterId(@Query("adoptionCenterId") adoptionCenterId: String): NAnimalsListResponse

    @POST("api/animals")
    suspend fun addAnimal(@Body data: NAnimalParam): NPostAnimalResponse

    @Multipart
    @PUT("api/animals/{id}/upload-image")
    suspend fun uploadAnimalImage(
        @Path("id") id: String,
        @Part image: MultipartBody.Part
    ): NUploadAsset

    @PUT("api/animals/{id}")
    suspend fun editAnimal(@Path("id") id: String, @Body data: NAnimalParam): NPostAnimalResponse

    @DELETE("api/animals/{id}")
    suspend fun deleteAnimal(@Path("id") id: String): NMessageResponse
}