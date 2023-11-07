package com.example.petadoptionapp.network.models

data class RegisterParams(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
)