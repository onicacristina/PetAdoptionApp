package com.example.petadoptionapp.network.models.response

import com.example.petadoptionapp.network.models.User

data class RegisterResponse(
    val message: String,
    val user: User,
    val token: String,
    val refreshToken: String
)
