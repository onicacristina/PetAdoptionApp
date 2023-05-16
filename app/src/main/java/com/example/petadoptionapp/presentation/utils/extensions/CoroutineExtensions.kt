package com.example.petadoptionapp.presentation.utils.extensions

import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration


fun tickerFlow(period: Duration) = tickerFlow(Duration.ZERO, period)

fun tickerFlow(initialDelay: Duration, period: Duration): Flow<Unit> = callbackFlow {
    var isActive = true

    // If there is an initial delay, we wait
    if (initialDelay != Duration.ZERO) {
        delay(initialDelay)
    }

    // While active, continuously delay and send a signal through the flow afterwards
    do {
        send(Unit)
        delay(period)
    } while (isActive)

    // If the flow is stopped, set isActive to false
    awaitClose { isActive = false }
}

fun <T> Flow<T>.withPrevious(initial: T? = null): Flow<ValueWithPrevious<T>> = flow {
    var previous = initial
    collect { value ->
        emit(ValueWithPrevious(previous, value))
        previous = value
    }
}

data class ValueWithPrevious<out T>(val previous: T?, val current: T)