package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.succes_appointment

import androidx.lifecycle.SavedStateHandle
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BookingAppointmentFinalViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel(),
    StateDelegate<BookingAppointmentFinalViewModel.State> by DefaultStateDelegate(State.default),
    EventDelegate<BookingAppointmentFinalViewModel.Event> by DefaultEventDelegate() {

    private val navArgs: BookingAppointmentFinalFragmentArgs by lazy {
        BookingAppointmentFinalFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    init {
        getAppointmentsDetails()
    }

    private fun getAppointmentsDetails() {
        currentState =
            currentState.copy(petName = navArgs.petName, appointmentTime = navArgs.appointmentTime)
    }

    data class State(
        val petName: String,
        val appointmentTime: String
    ) {
        companion object {
            val default = State("", "")
        }
    }

    enum class Event {
        FINISH
    }
}