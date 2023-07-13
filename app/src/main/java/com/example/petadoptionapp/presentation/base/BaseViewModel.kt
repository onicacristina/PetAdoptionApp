package com.example.petadoptionapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.APIResponseError
import com.example.petadoptionapp.network.ApiException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import retrofit2.HttpException
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {
    private val _error: Channel<String> = Channel()
    val error: Flow<String>
        get() = _error.receiveAsFlow()

//    fun showError(failure: Throwable) = viewModelScope.launch {
//        if (failure is ApiException){
//            failure.response?.globalErrors?.firstOrNull()?.message?.let { _error.send(it) }
//        }
//        else {
//            failure.message?.let {
//                _error.send(it)
//            }
//        }
//    }

    fun showError(failure: Throwable) = viewModelScope.launch {
        val errorMessage: String? = when (failure) {
            is ApiException -> failure.response?.globalErrors?.firstOrNull()?.message
            is HttpException -> {
                val statusCode = failure.code()
                val errorBody = failure.response()?.errorBody()?.string()
                Timber.e("HTTP error - status code: $statusCode, body: $errorBody")

                try {
                    val apiResponseError = Json.decodeFromString<APIResponseError>(errorBody ?: "")
                    val globalErrors = apiResponseError.globalErrors
                    if (globalErrors != null && globalErrors.isNotEmpty()) {
                        val errorMessage = globalErrors[0].message
                        Timber.e("Error message: $errorMessage")
                        errorMessage
                    } else {
                        null
                    }
                } catch (e: Exception) {
                    Timber.e("Failed to parse error body: ${e.message}")
                    null
                }
            }
            else -> failure.message
        }
        errorMessage?.let {
            _error.send(it)
        }
    }
}