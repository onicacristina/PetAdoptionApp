package com.example.petadoptionapp.network.models

data class User(
    val id: String,
    val role: Int = 0,
    val firstName: String,
    val lastName: String,
    val phone: String,
    val email: String,
    val createdAt: String
)
