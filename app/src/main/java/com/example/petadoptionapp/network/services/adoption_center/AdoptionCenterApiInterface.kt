package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAdoptionCenter

interface AdoptionCenterApiInterface {
    suspend fun getOneAdoptionCenterById(id: String): NAdoptionCenterResponse
    suspend fun getAllAdoptionCenters(): NAdoptionCentersListResponse

    suspend fun addAdoptionCenter(data: NAdoptionCenterParams): NPostAdoptionCenter
    suspend fun ediAdoptionCenter(id: String, data: NAdoptionCenterParams): NPostAdoptionCenter
    suspend fun deleteAdoptionCenter(id: String): NMessageResponse
}