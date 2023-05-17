package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse

interface AdoptionCenterApiInterface {
    suspend fun getOneAdoptionCenterById(id: String): NAdoptionCenterResponse
    suspend fun getAllAdoptionCenters(): NAdoptionCentersListResponse
}