package com.example.petadoptionapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * LanguageInterceptor is an OkHttp Interceptor used to modify HTTP requests by adding a language query parameter.
 *
 * @param language The desired language code to be added as a query parameter to the requests.
 *
 * This interceptor is particularly useful for scenarios where the server supports multilingual content,
 * and the client needs to specify the preferred language for the response.
 *
 * Usage:
 * ```kotlin
 * val languageInterceptor = LanguageInterceptor("en") // Specify the desired language code, e.g., "en" for English.
 * val okHttpClient = OkHttpClient.Builder()
 *     .addInterceptor(languageInterceptor)
 *     // Add other interceptors or configurations as needed.
 *     .build()
 * ```
 */
class LanguageInterceptor(private val language: String) : Interceptor {
    /**
     * Intercepts the HTTP request and adds the language query parameter to the URL.
     *
     * @param chain The interceptor chain to proceed with the modified request.
     * @return The response after processing the modified request.
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        // Retrieve the original request from the interceptor chain.
        val originalRequest: Request = chain.request()

        // Modify the URL of the original request by adding the language query parameter.
        val modifiedUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter("lang", language)
            .build()

        // Build a new request with the modified URL.
        val modifiedRequest: Request = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()

        // Proceed with the modified request in the interceptor chain and return the response.
        return chain.proceed(modifiedRequest)
    }
}