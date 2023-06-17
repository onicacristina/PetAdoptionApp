package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import com.example.petadoptionapp.presentation.utils.Constants
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("api/auth/login")
    suspend fun login(@Body loginParams: LoginParams): LoginResponse

    @POST("api/auth/register")
    suspend fun register(@Body registerParams: RegisterParams): RegisterResponse

    @POST("admin/auth/login")
    suspend fun loginAdmin(
        @Header("Authorization") authorization: String = Constants.NO_AUTH,
        @Body loginParams: LoginParams
    ): LoginResponse

    @POST("admin/auth/register")
    suspend fun registerAdmin(
        @Header("Authorization") authorization: String = Constants.NO_AUTH,
        @Body registerParams: RegisterParams
    ): RegisterResponse

}