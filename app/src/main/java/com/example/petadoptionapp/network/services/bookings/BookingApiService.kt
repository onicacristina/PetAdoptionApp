package com.example.petadoptionapp.network.services.bookings

import com.example.petadoptionapp.network.models.request.NBookingParams
import com.example.petadoptionapp.network.models.response.NBookingListResponse
import com.example.petadoptionapp.network.models.response.NBookingResponse
import com.example.petadoptionapp.network.models.response.NPostBookingResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingApiService {

    @GET("bookings/{id}")
    suspend fun getOneBookingById(@Path("id") id: String): NBookingResponse

    @GET("bookings")
    suspend fun getBookingsByUserId(@Query("userId") userId: String): NBookingListResponse

    @POST("bookings")
    suspend fun addBooking(@Body data: NBookingParams): NPostBookingResponse
}