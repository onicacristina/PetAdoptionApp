package com.example.petadoptionapp.repository.mapper.responses

import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.network.models.response.NAnimalResponse
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.repository.mapper.ModelMapper

class NAnimalResponseMapper : ModelMapper<NAnimalResponse, AnimalResponse> {
    override fun map(model: NAnimalResponse): AnimalResponse {
        return AnimalResponse(
            model.id,
            EPetCategory.getPetCategoryFromString(model.specie),
            EPetGender.getPetGenderFromString(model.gender),
            model.name,
            model.breed,
            model.age,
            model.vaccinated,
            model.neutered,
            model.story,
            model.imageUrl,
            model.adoptionCenterId,
            model.createdAt,
            model.updatedAt
        )
    }
}