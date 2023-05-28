package com.example.petadoptionapp.presentation.utils

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

interface EventDelegate<T> {

    val event: Flow<T>

    fun sendEvent(event: T)

}

class DefaultEventDelegate<T>(channelCapacity: Int = Channel.RENDEZVOUS) : EventDelegate<T> {

    private val _event: Channel<T> = Channel(channelCapacity)
    override val event: Flow<T>
        get() = _event.receiveAsFlow()

    override fun sendEvent(event: T) {
        _event.trySend(event)
    }

}