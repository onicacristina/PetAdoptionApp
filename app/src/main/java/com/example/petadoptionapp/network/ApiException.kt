package com.example.petadoptionapp.network

class ApiException(
    val code: Int,
    val response: APIResponseError? = null,
    message: String? = null,
) : Exception(message) {
}