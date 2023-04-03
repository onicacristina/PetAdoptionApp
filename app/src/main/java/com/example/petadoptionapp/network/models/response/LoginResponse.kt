package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.network.models.User
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    val message: String,
    val user: User,
    val token: String,
    @SerializedName("refresh_token")
    val refreshToken: String
)
