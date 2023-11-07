package com.example.petadoptionapp.presentation.utils

sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Error(val error: String) : Resource<Nothing>()
    data class Value<out T>(val data: T) : Resource<T>()
}