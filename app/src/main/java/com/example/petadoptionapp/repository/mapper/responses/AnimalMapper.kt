package com.example.petadoptionapp.repository.mapper.responses

import com.example.petadoptionapp.network.models.Animal
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.utils.Constants.PLACEHOLDER_PET_IMAGE
import com.example.petadoptionapp.repository.mapper.ModelMapper

class AnimalMapper : ModelMapper<AnimalResponse, Animal>{
    override fun map(model: AnimalResponse): Animal {
        return Animal(
            model.id,
            model.specie,
            model.gender,
            model.name,
            model.breed,
            model.age,
            model.vaccinated,
            model.neutered,
            model.story,
            model.adoptionCenterId,
            if (model.uploadedAssets.isNotEmpty()) model.uploadedAssets.first().path else PLACEHOLDER_PET_IMAGE,
            model.createdAt,
            model.updatedAt,
            model.isSaved
        )
    }
}