package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.network.services.auth.AuthApiInterface
import com.example.petadoptionapp.repository.extensions.Result
import com.example.petadoptionapp.repository.extensions.map
import com.example.petadoptionapp.repository.extensions.parseResponse
import javax.inject.Inject


class AuthRepository @Inject constructor(
    private val authApiInterface: AuthApiInterface
) : AuthRepositoryInterface {
    override suspend fun login(loginParams: LoginParams): Result<Failure, LoginResponse> {
        return authApiInterface.login(loginParams = loginParams).parseResponse().map { it }
    }

    override suspend fun register(registerParams: RegisterParams): Result<Failure, RegisterResponse> {
        val result = authApiInterface.register(registerParams = registerParams)
        val parseResponse = result.parseResponse()
//        return parseResponse.map { it }
        return authApiInterface.register(registerParams = registerParams).parseResponse().map { it }
    }
}