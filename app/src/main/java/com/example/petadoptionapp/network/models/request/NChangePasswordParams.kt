package com.example.petadoptionapp.network.models.request

data class NChangePasswordParams(
    val oldPassword: String,
    val newPassword: String,
    val confirmPassword: String
)
