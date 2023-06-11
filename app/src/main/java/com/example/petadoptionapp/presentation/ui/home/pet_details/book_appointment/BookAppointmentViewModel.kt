package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment

import androidx.lifecycle.SavedStateHandle
import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.model.AvailableHour
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import com.example.petadoptionapp.repository.booking.BookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(
    private val bookingRepository: BookingRepository,
    private val savedStateHandle: SavedStateHandle,
) : BaseViewModel(),
    StateDelegate<BookAppointmentViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<BookAppointmentViewModel.Event> by DefaultEventDelegate() {

    private val navArgs: BookAppointmentFragmentArgs by lazy {
        BookAppointmentFragmentArgs.fromSavedStateHandle(savedStateHandle)
    }

    init {
        getAvailableTimes()
        Timber.e("adoption center : ${navArgs.adoptionCenter}")
    }

    private fun getAvailableTimes() {
        val startTimeString = navArgs.adoptionCenter?.availableStart
        val endTimeString = navArgs.adoptionCenter?.availableEnd

        val startTime =
            startTimeString?.substring(11, 16) // Extragem doar partea corespunzătoare orei (09:00)
        val endTime =
            endTimeString?.substring(11, 16) // Extragem doar partea corespunzătoare orei (16:00)

        val availableHours = generateAvailableHours(startTime, endTime)
        currentState = State.Value(availableHours)
    }

    private fun generateAvailableHours(startTime: String?, endTime: String?): List<AvailableHour> {
        val availableHours = mutableListOf<AvailableHour>()
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        val startCalendar = Calendar.getInstance()
        startCalendar.time = timeFormat.parse(startTime)

        val endCalendar = Calendar.getInstance()
        endCalendar.time = timeFormat.parse(endTime)

        while (startCalendar.before(endCalendar)) {
            val hour = timeFormat.format(startCalendar.time)
            availableHours.add(AvailableHour(hour))
            startCalendar.add(Calendar.MINUTE, 30)
        }

        return availableHours
    }


    // Funcția pentru a genera lista de ore disponibile
    fun generateAvailableHours3(startDate: Date, endDate: Date): List<AvailableHour> {
        val availableHours = ArrayList<AvailableHour>()

        // Setăm formatul orei
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // Setăm intervalul de timp pentru programări
        val startCalendar = Calendar.getInstance()
        startCalendar.time = startDate

        val endCalendar = Calendar.getInstance()
        endCalendar.time = endDate

        // Parcurgem intervalul de timp și adăugăm orele disponibile în listă
        while (startCalendar.before(endCalendar)) {
            val hour = timeFormat.format(startCalendar.time)
            availableHours.add(AvailableHour(hour))

            // Adăugăm 30 de minute la ora curentă
            startCalendar.add(Calendar.MINUTE, 30)
        }

        return availableHours
    }

    sealed class State {
        object Loading : State()
        object Empty : State()
        data class Value(val petsList: List<AvailableHour>) : State()
    }

    enum class Event {
        FINISH
    }
}