package com.example.petadoptionapp.presentation.ui.appointments.past_appointments

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
class PastAppointmentsViewModel @Inject constructor(
    private val bookingRepository: BookingRepository
) : BaseViewModel(),
    StateDelegate<PastAppointmentsViewModel.State> by DefaultStateDelegate(State.Loading) {
    init {
        getPastAppointments()
    }

    private fun getPastAppointments() {
        val userRole = ProfilePrefs().getProfile()

        if (userRole?.role == 0)
            getPastAppointmentsUser()

        if (userRole?.role == 1)
            getPastAppointmentsAdmin()
    }

    private fun getPastAppointmentsUser() {
        viewModelScope.launch {
            val userId = ProfilePrefs().getProfile()?.id
            userId?.let { bookingRepository.getBookingsByUserId(userId = it) }?.fold(
                onSuccess = { data ->
                    val appointmentDateFormat = DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        Locale.getDefault()
                    )
                    val currentDate = LocalDate.now()
                    val pastAppointments = data.filter { appointment ->
                        val appointmentDate =
                            LocalDate.parse(appointment.dateAndTime, appointmentDateFormat)
                        appointmentDate.isBefore(currentDate)
                    }

                    currentState = if (pastAppointments.isEmpty()) State.Empty else State.Value(
                        pastAppointments
                    )
                },
                onFailure = { error ->
                    showError(error)
                    Timber.e("Error fetching bookings")
                }
            )
        }
    }

    private fun getPastAppointmentsAdmin() {
        viewModelScope.launch {
            val adoptionCenterId = ProfilePrefs().getProfile()?.adoptionCenterId
            adoptionCenterId?.let { bookingRepository.getBookingsByAdoptionCenterId(adoptionCenterId = it) }?.fold(
                onSuccess = { data ->
                    val appointmentDateFormat = DateTimeFormatter.ofPattern(
                        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                        Locale.getDefault()
                    )
                    val currentDate = LocalDate.now()
                    val pastAppointments = data.filter { appointment ->
                        val appointmentDate =
                            LocalDate.parse(appointment.dateAndTime, appointmentDateFormat)
                        appointmentDate.isBefore(currentDate)
                    }

                    currentState = if (pastAppointments.isEmpty()) State.Empty else State.Value(
                        pastAppointments
                    )
                },
                onFailure = { error ->
                    showError(error)
                    Timber.e("Error fetching bookings")
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