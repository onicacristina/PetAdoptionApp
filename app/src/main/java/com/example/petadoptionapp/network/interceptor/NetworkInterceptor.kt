package com.example.petadoptionapp.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class NetworkInterceptor(private val networkMonitor: NetworkMonitor) : Interceptor {

    /**
     * Method to intercept errors from Okhttp and Retrofit
     *
     * @throws InternetConnectivityException if some network exception happens
     */
    @Throws(NoInternetConnectionException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!networkMonitor.isConnected())
            throw NoInternetConnectionException()
        return try {
            chain.proceed(chain.request())
        } catch (e: SocketTimeoutException) {
            throw NoInternetConnectionException()
        } catch (e: UnknownHostException) {
            throw NoInternetConnectionException()
        }
    }

}