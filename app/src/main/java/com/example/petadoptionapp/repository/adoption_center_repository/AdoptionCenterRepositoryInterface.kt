package com.example.petadoptionapp.repository.adoption_center_repository

import com.example.petadoptionapp.network.models.AdoptionCenter

interface AdoptionCenterRepositoryInterface {
    suspend fun getOneAdoptionCenterById(id: String): Result<AdoptionCenter>
    suspend fun getAllAdoptionCenters(): Result<List<AdoptionCenter>>
}