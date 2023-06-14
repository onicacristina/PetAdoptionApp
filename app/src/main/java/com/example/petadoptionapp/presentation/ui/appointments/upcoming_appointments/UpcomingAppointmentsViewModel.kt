package com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments

import androidx.lifecycle.viewModelScope
import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.booking.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject

@HiltViewModel
class UpcomingAppointmentsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : BaseViewModel(),
    StateDelegate<UpcomingAppointmentsViewModel.State> by DefaultStateDelegate(State.Loading) {
    init {
        getUpcomingAppointments()
    }

    private fun getUpcomingAppointments() {
        viewModelScope.launch {
            val userId = ProfilePrefs().getProfile()?.id
            userId?.let { bookingRepository.getBookingsByUserId(userId = it) }?.fold(
                onSuccess = { data ->
                    val appointmentDateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                    val currentDate = LocalDate.now()
                    val upcomingAppointments = data.filter { appointment ->
                        val appointmentDate = LocalDate.parse(appointment.dateAndTime, appointmentDateFormat)
                        // Check if the appointment date is in the present or future
                        appointmentDate.isEqual(currentDate) || appointmentDate.isAfter(currentDate)
                    }

                    currentState = if (upcomingAppointments.isEmpty()) State.Empty else State.Value(upcomingAppointments)
                },
                onFailure = { error ->
                    showError(error)
                    Timber.e("Error fetching bookings")
                }
            )
        }
    }

    fun deleteAppointmentRequest(data: Booking) {
        viewModelScope.launch {
            bookingRepository.deleteBooking(data.id).fold(
                onSuccess = {
                    getUpcomingAppointments()
                },
                onFailure = { error ->
                    showError(error)
                }
            )
        }
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val appointments: List<Booking>) : State()
    }

}