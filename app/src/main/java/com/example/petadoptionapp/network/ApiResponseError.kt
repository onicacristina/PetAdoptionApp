package com.example.petadoptionapp.network

import kotlinx.serialization.Serializable

@Serializable
class APIResponseError(

    val fieldErrors: List<APIError>?,
    val globalErrors: List<APIError>?

)