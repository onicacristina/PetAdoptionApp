package com.example.petadoptionapp.presentation.ui.home

import com.example.petadoptionapp.network.models.response.AnimalResponse

sealed class PageUpdate {
    object Loading : PageUpdate()
    data class Error(val error: String) : PageUpdate()
    data class Value(val dataUpdate: List<AnimalResponse>) : PageUpdate()
}