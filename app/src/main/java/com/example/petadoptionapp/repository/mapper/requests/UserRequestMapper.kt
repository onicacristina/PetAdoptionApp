package com.example.petadoptionapp.repository.mapper.requests

import com.example.petadoptionapp.network.models.User
import com.example.petadoptionapp.network.models.response.NUserResponse
import com.example.petadoptionapp.repository.mapper.ModelMapper

class UserRequestMapper: ModelMapper<User, NUserResponse> {
    override fun map(model: User): NUserResponse {
        return NUserResponse(
            model.id,
            model.role,
            model.firstName ?: "",
            model.lastName ?: "",
            model.phone ?: "",
            model.email ?: "",
            model.createdAt ?: ""
        )
    }
}