package com.example.petadoptionapp.network.services.bookings

import com.example.petadoptionapp.network.models.request.NBookingParams
import com.example.petadoptionapp.network.models.response.NBookingListResponse
import com.example.petadoptionapp.network.models.response.NBookingResponse
import com.example.petadoptionapp.network.models.response.NMessageResponse
import com.example.petadoptionapp.network.models.response.NPostBookingResponse

interface BookingApiInterface {
    suspend fun getOneBookingById(id: String): NBookingResponse
    suspend fun getBookingsByUserId(userId: String): NBookingListResponse
    suspend fun getBookingsByAdoptionCenterId(adoptionCenterId: String): NBookingListResponse

    suspend fun addBooking(data: NBookingParams): NPostBookingResponse

    suspend fun deleteBooking(id: String): NMessageResponse
}