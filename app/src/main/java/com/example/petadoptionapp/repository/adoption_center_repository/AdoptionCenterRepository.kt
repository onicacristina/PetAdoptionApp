package com.example.petadoptionapp.repository.adoption_center_repository

import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.request.NAdoptionCenterParams
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostAdoptionCenter
import com.example.petadoptionapp.network.services.adoption_center.AdoptionCenterApiInterface
import com.example.petadoptionapp.repository.mapper.responses.NAdoptionCenterResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AdoptionCenterRepository @Inject constructor(
    private val apiAdoptionCentersInterface: AdoptionCenterApiInterface
) : AdoptionCenterRepositoryInterface {
    override suspend fun getOneAdoptionCenterById(id: String): Result<AdoptionCenter> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val result = apiAdoptionCentersInterface.getOneAdoptionCenterById(id)
                NAdoptionCenterResponseMapper().map(result)
            }
        }
    }

    override suspend fun getAllAdoptionCenters(): Result<List<AdoptionCenter>> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAdoptionCentersInterface.getAllAdoptionCenters().results.map {
                    NAdoptionCenterResponseMapper().map(
                        it
                    )
                }
            }
        }
    }

    override suspend fun addAdoptionCenter(data: NAdoptionCenterParams): Result<NPostAdoptionCenter> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAdoptionCentersInterface.addAdoptionCenter(data)
            }
        }
    }

    override suspend fun editAdoptionCenter(
        id: String,
        data: NAdoptionCenterParams
    ): Result<NPostAdoptionCenter> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAdoptionCentersInterface.ediAdoptionCenter(id = id, data = data)
            }
        }
    }

    override suspend fun deleteAdoptionCenter(id: String): Result<NMessageResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiAdoptionCentersInterface.deleteAdoptionCenter(id = id)
            }
        }
    }
}