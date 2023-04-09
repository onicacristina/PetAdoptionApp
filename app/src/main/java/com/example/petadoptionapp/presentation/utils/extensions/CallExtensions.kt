//package com.example.petadoptionapp.presentation.utils.extensions
//
//import android.util.Log
//import com.example.petadoptionapp.network.Failure
//import com.example.petadoptionapp.network.interceptor.NoInternetConnectionException
//import kotlinx.coroutines.suspendCancellableCoroutine
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.HttpException
//import retrofit2.Response
//import java.io.IOException
//import kotlin.coroutines.resume
//
//inline fun <T> apiCall(block: () -> T): T = try {
//    block()
//} catch (error: Throwable) {
//    throw when (error) {
//        is NoInternetConnectionException -> error
//        is IOException -> NoInternetConnectionException()
//        is HttpException -> {
//            val code = error.code()
//            val errorResponse = error.response()?.errorBody()?.string() ?: ""
//            val errorResponseBody = runCatching { Json.decodeFromString<APIResponseError>(errorResponse) }
//            errorResponseBody.fold(
//                onSuccess = { errorBody -> ApiException(code, errorBody) },
//                onFailure = { e -> e }
//            )
//        }
//        else -> NoInternetConnectionException()
//    }
//}
//
//inline fun <T> apiCallCatching(block: () -> T): kotlin.Result<T> = runCatching { apiCall { block() } }
//
//@OptIn(ExperimentalSerializationApi::class)
//suspend fun <T : Any> Call<T>.parseResponse(): Result<Failure, T> {
//    return suspendCancellableCoroutine { continuation ->
//        this.enqueue(object : Callback<T> {
//            override fun onResponse(call: Call<T>, response: Response<T>) {
//                if (continuation.isCancelled) return
//                if (response.isSuccessful)
//                    continuation.resume(response.body()?.let {
//                        Result.Value(it)
//                    }
//                        ?: run { Result.Error(Failure.ResourceNotAvailableFailure("Resource not available")) })
//                else
//                    continuation.resume(response.errorBody()?.let {
//
//                        val errorBody = it.string()
//                        Log.d("log", errorBody)
//
//                        when {
//                            response.code() in 400..500 -> {
//                                var errorResponse: APIResponseError? = null
//                                try {
//                                    errorResponse = Json.decodeFromString<APIResponseError>(errorBody)
//                                } catch (e: Exception) {
//                                    Log.d("log", e.message.toString())
//                                }
//
//                                errorResponse?.let { errorResp ->
//                                    val errorMessage: String =
//                                        errorResp.globalErrors?.let { errors ->
//                                            if (errors.isEmpty())
//                                                errorBody
//                                            else errors[0].message
//                                        } ?: errorBody
//                                    if (response.code() == 409)
//                                        Result.Error(
//                                            Failure.InvalidCredentialsErrorFailure(errorMessage)
//                                        )
//                                    else
//                                        Result.Error(Failure.ApiErrorFailure(errorMessage))
//                                }
//
//                            }
//                            else -> Result.Error(Failure.ResourceNotAvailableFailure(it.toString()))
//                        }
//                    }
//                        ?: run { Result.Error(Failure.ServerFailure("Server responded with unsuccessful response code")) })
//            }
//
//            override fun onFailure(call: Call<T>, t: Throwable) {
//                // Don't bother with resuming the continuation if it is already cancelled.
//                if (continuation.isCancelled) return
//                Log.d("network", "Network failure exception: $t")
//                when (t) {
//                    is NoInternetConnectionException -> continuation.resume(
//                        Result.Error(
//                            Failure.NetworkConnectionFailure(
//                                "There is no internet access"
//                            )
//                        )
//                    )
//                    is MissingFieldException, is SerializationException -> continuation.resume(
//                        Result.Error(
//                            Failure.ResourceParseFailure(
//                                "The requested json response could not be parsed"
//                            )
//                        )
//                    )
//                    else -> continuation.resume(Result.Error(Failure.NetworkConnectionFailure("Server did not respond, there is a problem with the connection")))
//                }
//            }
//        })
//    }
//}