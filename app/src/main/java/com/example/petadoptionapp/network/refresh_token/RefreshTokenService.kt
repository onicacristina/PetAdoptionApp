package com.example.petadoptionapp.network.refresh_token

import com.example.petadoptionapp.network.interceptor.JwtTokenInterceptor
import com.example.petadoptionapp.network.models.response.LoginResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RefreshTokenService {

    @GET("api/auth/refresh-token")
    fun refreshToken(@Header(JwtTokenInterceptor.AUTHORIZATION_KEY) header: String): Call<LoginResponse>

    @GET("admin/auth/refresh-token")
    fun refreshTokenAdmin(@Header(JwtTokenInterceptor.AUTHORIZATION_KEY) header: String): Call<LoginResponse>

}