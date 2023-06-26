package com.example.petadoptionapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class LanguageInterceptor(private val language: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest: Request = chain.request()
        val modifiedUrl = originalRequest.url
            .newBuilder()
            .addQueryParameter("lang", language)
            .build()
        val modifiedRequest: Request = originalRequest.newBuilder()
            .url(modifiedUrl)
            .build()
        return chain.proceed(modifiedRequest)
    }
}