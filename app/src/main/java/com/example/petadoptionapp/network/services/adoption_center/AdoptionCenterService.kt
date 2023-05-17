package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface AdoptionCenterService {

    @GET("adoption-centers/{id}")
    suspend fun getOneAdoptionCenterById(@Path("id") id: String) : NAdoptionCenterResponse

    @GET("adoption-centers")
    suspend fun getAllAdoptionCenters(): NAdoptionCentersListResponse
}