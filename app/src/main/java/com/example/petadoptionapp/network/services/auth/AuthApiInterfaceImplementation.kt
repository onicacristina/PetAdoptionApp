package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import javax.inject.Inject

class AuthApiInterfaceImplementation @Inject constructor(
    private val authApiService: AuthApiService
) : AuthApiInterface {
    override suspend fun login(loginParams: LoginParams): LoginResponse {
        return authApiService.login(loginParams = loginParams)
    }

    override suspend fun register(registerParams: RegisterParams): RegisterResponse {
        return authApiService.register(registerParams = registerParams)
    }

    override suspend fun loginAdmin(loginParams: LoginParams): LoginResponse {
        return authApiService.loginAdmin(loginParams = loginParams)
    }

    override suspend fun registerAdmin(registerParams: RegisterParams): RegisterResponse {
        return authApiService.registerAdmin(registerParams = registerParams)
    }
}