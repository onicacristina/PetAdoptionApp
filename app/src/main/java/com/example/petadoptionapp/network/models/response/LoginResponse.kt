package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.network.models.User

data class LoginResponse(
    val message: String,
    val user: User,
    val token: String,
    val refreshToken: String
)
