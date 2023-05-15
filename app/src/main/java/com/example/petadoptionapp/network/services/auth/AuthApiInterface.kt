package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse

interface AuthApiInterface {

    suspend fun login(loginParams: LoginParams): LoginResponse
    suspend fun register(registerParams: RegisterParams): RegisterResponse
}
