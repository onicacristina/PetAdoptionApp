package com.example.petadoptionapp.network.services.user

import com.example.petadoptionapp.network.models.response.NUserEditResponse
import com.example.petadoptionapp.network.models.response.NUserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserApiService {

    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): NUserResponse

    @PUT("users/{id}")
    suspend fun editUser(@Path("id") id: String, @Body data: NUserResponse): NUserEditResponse

}