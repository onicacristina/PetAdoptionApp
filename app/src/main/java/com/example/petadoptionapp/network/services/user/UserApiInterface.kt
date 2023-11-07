package com.example.petadoptionapp.network.services.user

import com.example.petadoptionapp.network.models.response.NUserEditResponse
import com.example.petadoptionapp.network.models.response.NUserResponse

interface UserApiInterface {
    suspend fun getUserById(id: String): NUserResponse
    suspend fun editUser(id: String, data: NUserResponse): NUserEditResponse
}