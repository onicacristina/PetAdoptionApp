package com.example.petadoptionapp.network.services.adoption_center

import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.network.models.response.NAdoptionCentersListResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAdoptionCenter
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

    override suspend fun addAdoptionCenter(data: NAdoptionCenterParams): NPostAdoptionCenter {
        return adoptionCenterService.addAdoptionCenter(data)
    }

    override suspend fun ediAdoptionCenter(
        id: String,
        data: NAdoptionCenterParams
    ): NPostAdoptionCenter {
        return adoptionCenterService.editAdoptionCenter(id = id, data = data)
    }

    override suspend fun deleteAdoptionCenter(id: String): NMessageResponse {
        return adoptionCenterService.deleteAdoptionCenter(id = id)
    }

}