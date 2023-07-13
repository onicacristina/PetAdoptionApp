package com.example.petadoptionapp.repository.adoption_center_repository

import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAdoptionCenter

interface AdoptionCenterRepositoryInterface {
    suspend fun getOneAdoptionCenterById(id: String): Result<AdoptionCenter>
    suspend fun getAllAdoptionCenters(): Result<List<AdoptionCenter>>

    suspend fun addAdoptionCenter(data: NAdoptionCenterParams): Result<NPostAdoptionCenter>
    suspend fun editAdoptionCenter(id: String, data: NAdoptionCenterParams): Result<NPostAdoptionCenter>
    suspend fun deleteAdoptionCenter(id: String): Result<NMessageResponse>
}