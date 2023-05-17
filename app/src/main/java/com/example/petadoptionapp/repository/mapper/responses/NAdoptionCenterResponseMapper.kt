package com.example.petadoptionapp.repository.mapper.responses

import com.example.petadoptionapp.network.models.AdoptionCenter
import com.example.petadoptionapp.network.models.response.NAdoptionCenterResponse
import com.example.petadoptionapp.repository.mapper.ModelMapper

class NAdoptionCenterResponseMapper : ModelMapper<NAdoptionCenterResponse, AdoptionCenter> {
    override fun map(model: NAdoptionCenterResponse): AdoptionCenter {
        return AdoptionCenter(
            model.id,
            model.name,
            model.email,
            model.phone,
            model.address,
            model.city,
            model.availableStart,
            model.availableEnd
        )
    }
}