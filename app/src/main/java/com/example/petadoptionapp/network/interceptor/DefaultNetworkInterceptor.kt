package com.example.petadoptionapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class DefaultHeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
            .newBuilder()
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .build()
        return chain.proceed(request)
    }

}