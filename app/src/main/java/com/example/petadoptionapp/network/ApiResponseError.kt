package com.example.petadoptionapp.network

class APIResponseError(

    val fieldErrors: List<APIError>?,
    val globalErrors: List<APIError>?

)