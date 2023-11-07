package com.example.petadoptionapp.network.services.user

import com.example.petadoptionapp.network.models.response.NUserEditResponse
import com.example.petadoptionapp.network.models.response.NUserResponse
import javax.inject.Inject

class UserApiInterfaceImplementation @Inject constructor(
    private val userApiService: UserApiService
) : UserApiInterface {

    override suspend fun getUserById(id: String): NUserResponse {
        return userApiService.getUserById(id)
    }

    override suspend fun editUser(id: String, data: NUserResponse): NUserEditResponse {
        return userApiService.editUser(id, data)
    }
}