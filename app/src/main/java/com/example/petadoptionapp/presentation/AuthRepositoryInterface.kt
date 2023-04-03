package com.example.petadoptionapp.presentation

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.repository.Failure
import com.example.petadoptionapp.repository.extensions.Result


interface AuthRepositoryInterface {
    suspend fun login(loginParams: LoginParams): Result<Failure, LoginResponse>
    suspend fun register(registerParams: RegisterParams): Result<Failure, RegisterResponse>
}