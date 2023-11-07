package com.example.petadoptionapp.network

import kotlinx.serialization.Serializable

@Serializable
class APIError(
    val field: String,
    val message: String
)