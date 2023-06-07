package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment

import com.example.petadoptionapp.presentation.base.BaseViewModel
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.model.AvailableHour
import com.example.petadoptionapp.presentation.utils.DefaultEventDelegate
import com.example.petadoptionapp.presentation.utils.DefaultStateDelegate
import com.example.petadoptionapp.presentation.utils.EventDelegate
import com.example.petadoptionapp.presentation.utils.StateDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BookAppointmentViewModel @Inject constructor(

) : BaseViewModel(),
    StateDelegate<BookAppointmentViewModel.State> by DefaultStateDelegate(State.Loading),
    EventDelegate<BookAppointmentViewModel.Event> by DefaultEventDelegate() {

    init {
        getAvailableTimes()
    }

    private fun getAvailableTimes() {
        val startTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse("09:00")
        val endTime = SimpleDateFormat("HH:mm", Locale.getDefault()).parse("18:00")

        // Generăm lista de ore disponibile
        val availableHours = generateAvailableHours(startTime, endTime)

        currentState = State.Value(availableHours)
    }

    // Funcția pentru a genera lista de ore disponibile
    fun generateAvailableHours(startDate: Date, endDate: Date): List<AvailableHour> {
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