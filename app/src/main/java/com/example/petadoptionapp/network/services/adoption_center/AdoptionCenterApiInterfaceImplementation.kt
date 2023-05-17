package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse
import javax.inject.Inject

class AdoptionCenterApiInterfaceImplementation @Inject constructor(
    private val adoptionCenterService: AdoptionCenterService
) : AdoptionCenterApiInterface {

    override suspend fun getOneAdoptionCenterById(id: String): NAdoptionCenterResponse {
        return adoptionCenterService.getOneAdoptionCenterById(id)
    }

    override suspend fun getAllAdoptionCenters(): NAdoptionCentersListResponse {
        return adoptionCenterService.getAllAdoptionCenters()
    }

}