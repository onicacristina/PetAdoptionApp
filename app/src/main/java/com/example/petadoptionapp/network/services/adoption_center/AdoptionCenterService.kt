package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAdoptionCenter
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AdoptionCenterService {

    @GET("adoption-centers/{id}")
    suspend fun getOneAdoptionCenterById(@Path("id") id: String) : NAdoptionCenterResponse

    @GET("adoption-centers")
    suspend fun getAllAdoptionCenters(): NAdoptionCentersListResponse

    @POST("adoption-centers")
    suspend fun addAdoptionCenter(@Body data: NAdoptionCenterParams): NPostAdoptionCenter

    @PUT("adoption-centers/{id}")
    suspend fun editAdoptionCenter(@Path("id") id: String, @Body data: NAdoptionCenterParams): NPostAdoptionCenter

    @DELETE("adoption-centers/{id}")
    suspend fun deleteAdoptionCenter(@Path("id") id: String): NMessageResponse
}