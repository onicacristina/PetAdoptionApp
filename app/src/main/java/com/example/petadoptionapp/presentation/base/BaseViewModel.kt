package com.example.petadoptionapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.ApiException
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

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
        val errorMessage = when (failure) {
            is ApiException -> failure.response?.globalErrors?.firstOrNull()?.message
            else -> failure.message
        }
        errorMessage?.let {
            _error.send(it)
        }
    }
}