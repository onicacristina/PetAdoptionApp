package com.example.petadoptionapp.repository.booking

import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.network.models.request.NBookingParams
import com.example.petadoptionapp.network.models.response.NPostBookingResponse

interface BookingRepositoryInterface {
    suspend fun getOneBookingById(id: String): Result<Booking>
    suspend fun getBookingsByUserId(userId: String): Result<List<Booking>>
    suspend fun addBooking(data: NBookingParams): Result<NPostBookingResponse>
}