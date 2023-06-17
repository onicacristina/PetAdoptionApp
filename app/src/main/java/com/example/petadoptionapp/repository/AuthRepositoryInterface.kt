package com.example.petadoptionapp.repository

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse


interface AuthRepositoryInterface {

    suspend fun register(registerParams: RegisterParams): Result<RegisterResponse>
    suspend fun login(loginParams: LoginParams): Result<LoginResponse>

    suspend fun registerAdmin(registerParams: RegisterParams): Result<RegisterResponse>
    suspend fun loginAdmin(loginParams: LoginParams): Result<LoginResponse>
}