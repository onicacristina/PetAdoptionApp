package com.example.petadoptionapp.network.models.response

data class NUserResponse(
    val id: String,
    val role: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val createdAt: String
)
