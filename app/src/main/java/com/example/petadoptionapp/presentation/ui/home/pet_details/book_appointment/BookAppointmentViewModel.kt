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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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

    private val _hoursList: MutableStateFlow<List<AvailableHour>> =
        MutableStateFlow(emptyList())
    val hoursList: Flow<List<AvailableHour>>
        get() = _hoursList

    private var appointmentDate: Date = Calendar.getInstance().time
    private var selectedHour: AvailableHour = AvailableHour("")
    private var appointmentTimeFinal: String = ""

    init {
        getAvailableTimes()
        Timber.e("adoption center : ${navArgs.adoptionCenter}")
        Timber.e("selected date: $appointmentDate")

    }

    fun onSelectedDate(date: Date) {
        appointmentDate = date
        Timber.e("selected date: $appointmentDate")
    }

    private fun getAvailableTimes() {
        val startTimeString = navArgs.adoptionCenter?.availableStart
        val endTimeString = navArgs.adoptionCenter?.availableEnd

        // We extract only the part corresponding to the time (ex. 09:00)
        val startTime = startTimeString?.substring(11, 16)
        // We extract only the part corresponding to the time (ex. 16:00)
        val endTime = endTimeString?.substring(11, 16)

        val availableHours = generateAvailableHours(startTime, endTime)
        currentState = State.Value(availableHours)
        _hoursList.value = availableHours
    }

    // Function to generate the list of available hours
    private fun generateAvailableHours(startTime: String?, endTime: String?): List<AvailableHour> {
        val availableHours = mutableListOf<AvailableHour>()
        // Set the time format
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        // We set the time interval for appointments
        val startCalendar = Calendar.getInstance()
        startCalendar.time = timeFormat.parse(startTime)

        val endCalendar = Calendar.getInstance()
        endCalendar.time = timeFormat.parse(endTime)

        // Loop through the timeslot and add the available times to the list
        while (startCalendar.before(endCalendar)) {
            val hour = timeFormat.format(startCalendar.time)
            availableHours.add(AvailableHour(hour))
            // Add 30 minutes to the current time
            startCalendar.add(Calendar.MINUTE, 30)
        }

        return availableHours
    }

    fun selectHour(availableHour: AvailableHour) {
        val oldData = _hoursList.value
        val newData = oldData.map { value ->
            val isSelected = value.hour == availableHour.hour
            value.copy(isSelected = isSelected)
        }
        _hoursList.value = newData
        currentState = State.Value(newData)
        selectedHour = availableHour
    }

    private fun getAppointmentTime(): String {
        val appointmentDateFormat = SimpleDateFormat("MMM d, yyyy, HH:mm:ss", Locale.getDefault())
        val formattedAppointmentDate = appointmentDateFormat.format(appointmentDate)
        // Combine the formatted date with the selected time
        return "$formattedAppointmentDate, ${selectedHour.hour}"
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