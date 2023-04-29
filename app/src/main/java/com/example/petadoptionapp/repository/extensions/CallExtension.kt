package com.example.petadoptionapp.repository.extensions

import android.util.MalformedJsonException
import com.example.petadoptionapp.network.APIResponseError
import com.example.petadoptionapp.presentation.utils.Constants.NETWORK_ERROR_CODE
import com.example.petadoptionapp.repository.Failure
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.suspendCancellableCoroutine
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.resume


suspend fun <T : Any> Call<T>.parseResponse(): Result<Failure, T> {
    return suspendCancellableCoroutine { continuation ->
        this.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (continuation.isCancelled) return
                if (response.isSuccessful) continuation.resume(response.body()?.let {
                    Result.Value(it)
                } ?: run { Result.Error(Failure("Resource not available")) })
                else continuation.resume(response.errorBody()?.let {

                    val errorBody = it.string()
                    Timber.d(errorBody)

                    when {
                        (response.code() == 521) -> {
                            Result.Error(
                                Failure("ProfileNotCompletedError")
                            )
                        }

                        (response.code() in 400..500) || (response.code() > 500) -> {
                            var errorResponse: APIResponseError? = null
                            try {
                                errorResponse =
                                    Gson().fromJson(errorBody, APIResponseError::class.java)
                            } catch (e: Exception) {
                                Timber.d(e.message)
                            }

                            errorResponse?.let { errorResp ->
                                val errorMessage: String = errorResp.globalErrors?.let { errors ->
                                    if (errors.isEmpty()) errorBody
                                    else errors[0].message
                                } ?: errorBody
                                Result.Error(Failure(errorMessage))
                            }

                        }

                        else -> Result.Error(Failure(it.toString()))
                    }
                }
                    ?: run { Result.Error(Failure("Server responded with unsuccessful response code")) })
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                t.printStackTrace()
                // Don't bother with resuming the continuation if it is already cancelled.
                if (continuation.isCancelled) return
                Timber.d("Network failure exception: $t")
                // it does not work with when; really odd
                when (t) {
                    is ConnectException,
                    is SocketTimeoutException,
                    is UnknownHostException -> continuation.resume(
                        Result.Error(
                            Failure(
                                "There is no internet access", NETWORK_ERROR_CODE
                            )
                        )
                    )
                    is MalformedJsonException, is JsonSyntaxException -> continuation.resume(
                        Result.Error(
                            Failure(
                                "The requested json response could not be parsed"
                            )
                        )
                    )
                    else -> continuation.resume(Result.Error(Failure("Server did not respond, there is a problem with the connection")))
                }
            }
        })
    }
}