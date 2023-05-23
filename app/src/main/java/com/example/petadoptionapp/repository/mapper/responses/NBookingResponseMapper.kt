package com.example.petadoptionapp.repository.mapper.responses

import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.network.models.response.NBookingResponse
import com.example.petadoptionapp.repository.mapper.ModelMapper

class NBookingResponseMapper : ModelMapper<NBookingResponse, Booking> {
    override fun map(model: NBookingResponse): Booking {
        return Booking(
            model.id,
            model.userid,
            model.adoptionCenterId,
            model.dateAndTime
        )
    }
}