package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import retrofit2.Call

interface AuthApiInterface {

    suspend fun login(loginParams: LoginParams): Call<LoginResponse>
    suspend fun register(registerParams: RegisterParams): Call<RegisterResponse>
}
