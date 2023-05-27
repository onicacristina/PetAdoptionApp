package com.example.petadoptionapp.network.services.bookings

import com.example.petadoptionapp.network.models.request.NBookingParams
import com.example.petadoptionapp.network.models.response.NBookingListResponse
import com.example.petadoptionapp.network.models.response.NBookingResponse
import com.example.petadoptionapp.network.models.response.NPostBookingResponse
import javax.inject.Inject

class BookingApiInterfaceImplementation @Inject constructor(
    private val bookingApiService: BookingApiService
): BookingApiInterface {
    override suspend fun getOneBookingById(id: String): NBookingResponse {
        return bookingApiService.getOneBookingById(id)
    }

    override suspend fun getBookingsByUserId(userId: String): NBookingListResponse {
        return bookingApiService.getBookingsByUserId(userId)
    }

    override suspend fun addBooking(data: NBookingParams): NPostBookingResponse {
        return bookingApiService.addBooking(data)
    }
}