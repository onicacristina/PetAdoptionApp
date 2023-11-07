package com.example.petadoptionapp.network.interceptor

interface NetworkMonitor {
    fun isConnected(): Boolean
}