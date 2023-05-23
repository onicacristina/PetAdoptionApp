package com.example.petadoptionapp.network.models

data class User(
    val id: String = "",
    val role: Int = 0,
    val firstName: String? = null,
    val lastName: String? = null ,
    val phone: String? = null,
    val email: String? = null,
    val createdAt: String? = null
)
