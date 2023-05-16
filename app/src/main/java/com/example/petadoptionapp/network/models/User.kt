package com.example.petadoptionapp.network.models

data class User(
    val fullName: String,
    val role: Int = 0,
    val email: String,
    val createdAt: String
)
