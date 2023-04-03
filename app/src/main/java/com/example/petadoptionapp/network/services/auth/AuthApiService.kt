package com.example.petadoptionapp.network.services.auth

import com.example.petadoptionapp.network.models.LoginParams
import com.example.petadoptionapp.network.models.RegisterParams
import com.example.petadoptionapp.network.models.response.LoginResponse
import com.example.petadoptionapp.network.models.response.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("user/auth/login")
    fun login(@Body loginParams: LoginParams) : Call<LoginResponse>

    @POST("user/auth/register")
    fun register(@Body registerParams: RegisterParams) : Call<RegisterResponse>


}