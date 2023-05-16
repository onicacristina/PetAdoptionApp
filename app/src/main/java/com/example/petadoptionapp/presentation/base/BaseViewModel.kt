package com.example.petadoptionapp.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {
    protected val _error: Channel<String> = Channel()
    val error: Flow<String>
        get() = _error.receiveAsFlow()

    fun showError(failure: Throwable) = viewModelScope.launch {
        failure.message?.let {
            _error.send(it)
        }
    }
}