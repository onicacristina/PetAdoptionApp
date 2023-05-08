package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.repository.extensions.Result


interface AuthRepositoryInterface {

    suspend fun register(registerParams: RegisterParams): Result<Failure, RegisterResponse>

    suspend fun login(loginParams: LoginParams): Result<Failure, LoginResponse>
}