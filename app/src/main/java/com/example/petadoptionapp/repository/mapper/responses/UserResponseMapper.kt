package com.example.petadoptionapp.repository.mapper.responses

import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.network.models.response.NUserResponse
import com.example.petadoptionapp.repository.mapper.ModelMapper

class UserResponseMapper: ModelMapper<NUserResponse, User> {
    override fun map(model: NUserResponse): User {
        return User(
            model.id,
            model.role,
            model.firstName,
            model.lastName,
            model.phone,
            model.email,
            model.createdAt
        )
    }
}