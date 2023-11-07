package com.example.petadoptionapp.repository.booking

import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.network.models.request.NBookingParams
import com.example.petadoptionapp.network.models.response.NPostBookingResponse
import com.example.petadoptionapp.network.services.bookings.BookingApiInterface
import com.example.petadoptionapp.repository.animals_repository.AnimalsRepository
import com.example.petadoptionapp.repository.mapper.responses.NBookingResponseMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookingRepository @Inject constructor(
    private val apiBookingInterface: BookingApiInterface,
    private val animalsRepository: AnimalsRepository
) : BookingRepositoryInterface {

    override suspend fun getOneBookingById(id: String): Result<Booking> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                val result = apiBookingInterface.getOneBookingById(id)
                NBookingResponseMapper().map(result)
            }
        }
    }

    override suspend fun getBookingsByUserId(userId: String): Result<List<Booking>> {
        var booking: Booking = Booking.default
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiBookingInterface.getBookingsByUserId(userId).results.map { bookingResponse ->
                    animalsRepository.getOneAnimalById(bookingResponse.animal.id)
                        .onSuccess { animalResponse ->
                            booking = Booking(
                                id = bookingResponse.id,
                                user = bookingResponse.user,
                                adoptionCenter = bookingResponse.adoptionCenter,
                                animal = animalResponse,
                                dateAndTime = bookingResponse.dateAndTime
                            )
                        }
                    booking
                }
            }
        }
    }

    override suspend fun getBookingsByAdoptionCenterId(adoptionCenterId: String): Result<List<Booking>> {
        var booking: Booking = Booking.default
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiBookingInterface.getBookingsByAdoptionCenterId(adoptionCenterId).results.map { bookingResponse ->
                    animalsRepository.getOneAnimalById(bookingResponse.animal.id)
                        .onSuccess { animalResponse ->
                            booking = Booking(
                                id = bookingResponse.id,
                                user = bookingResponse.user,
                                adoptionCenter = bookingResponse.adoptionCenter,
                                animal = animalResponse,
                                dateAndTime = bookingResponse.dateAndTime
                            )
                        }
                    booking
                }
            }
        }
    }

    override suspend fun addBooking(data: NBookingParams): Result<NPostBookingResponse> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiBookingInterface.addBooking(data)
            }
        }
    }

    override suspend fun deleteBooking(id: String): Result<Any> {
        return withContext(Dispatchers.IO) {
            kotlin.runCatching {
                apiBookingInterface.deleteBooking(id)
            }
        }
    }
}