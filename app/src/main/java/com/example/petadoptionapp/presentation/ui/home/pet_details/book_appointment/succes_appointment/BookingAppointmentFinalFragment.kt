package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.succes_appointment

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.CalendarContract
import android.text.SpannableString
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentBookingAppointmentFinalBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.addClickableLink
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import kotlinx.coroutines.launch
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val CALENDAR_PERMISSION_REQUEST = 100

class BookingAppointmentFinalFragment :
    NoBottomNavigationFragment<FragmentBookingAppointmentFinalBinding>(R.layout.fragment_booking_appointment_final) {
    override val viewBinding: FragmentBookingAppointmentFinalBinding by viewBinding(
        FragmentBookingAppointmentFinalBinding::bind
    )
    override val viewModel: BookingAppointmentFinalViewModel by viewModels()
    private lateinit var startTime: String
    private lateinit var petName: String
    private lateinit var appointmentLocation : String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservables()
    }

    private fun initViews() {

    }

    private fun initListeners() {
        viewBinding.btnGoToAppointments.setOnDebounceClickListener {
            //todo
        }
        viewBinding.btnAddToCalendar.setOnDebounceClickListener {
            //todo
            checkCalendarPermissions()
        }
    }

    private fun initObservables() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        render(value)
                    }
                }
            }
        }
    }

    private fun render(state: BookingAppointmentFinalViewModel.State) {
        startTime = state.appointmentTime
        petName = state.petName
        appointmentLocation = state.appointmentLocation
        viewBinding.tvTime.text = startTime
        viewBinding.tvSubtitle.addClickableLink(
            fullText = getString(R.string.waiting_for_you, petName),
            linkText = SpannableString(petName),
            context = requireContext(),
            isBolded = true,
            isUnderlined = false,
            textColor = R.color.text_black
        ) {}
    }


    private fun insertEventToCalendar() {
        val contentResolver: ContentResolver = requireContext().contentResolver
        val calendar = Calendar.getInstance()

        // The format to convert the string to the Data object
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        try {
            val startTime: Date = dateFormat.parse(transformDateFormat(startTime))
            calendar.time = startTime
            val startTimeInMillis = calendar.timeInMillis
            calendar.add(Calendar.MINUTE, 30)
            val endTimeInMillis = calendar.timeInMillis
            val title = getString(R.string.calendar_appointment_title)
            val description = getString(R.string.calendar_appointment_description, petName)
            val location = appointmentLocation
            // Insert the event
            val values = ContentValues().apply {
                put(CalendarContract.Events.CALENDAR_ID, 1)
                put(CalendarContract.Events.TITLE, title)
                put(CalendarContract.Events.DESCRIPTION, description)
                put(CalendarContract.Events.DTSTART, startTimeInMillis)
                put(CalendarContract.Events.DTEND, endTimeInMillis)
                //todo add location
                put(CalendarContract.Events.EVENT_LOCATION, location)
                put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().id)
            }

            val uri = contentResolver.insert(CalendarContract.Events.CONTENT_URI, values)

            // Display a toast message indicating the result
            if (uri != null) {
                val eventId = uri.lastPathSegment?.toLong()
                if (eventId != null) {
                    addNotifications(contentResolver, eventId)
                }
                Toast.makeText(requireContext(), getString(R.string.event_added_to_calendar), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.failed_to_add_event_to_calendar),
                    Toast.LENGTH_SHORT
                ).show()
            }

        } catch (e: ParseException) {
            // Error handling if the string cannot be converted to a valid date
            Toast.makeText(requireContext(), getString(R.string.invalid_start_time_format), Toast.LENGTH_SHORT).show()
        }
    }


    private fun checkCalendarPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.WRITE_CALENDAR
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            requestCalendarPermissions()
        } else {
            insertEventToCalendar()
        }
    }

    private fun requestCalendarPermissions() {
        requestPermissions(
            arrayOf(Manifest.permission.WRITE_CALENDAR), CALENDAR_PERMISSION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == CALENDAR_PERMISSION_REQUEST) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Calendar permission has been granted
                insertEventToCalendar()
            } else {
                // Calendar permission was not granted
                Toast.makeText(requireContext(), getString(R.string.calendar_permission_denied), Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


    private fun addNotifications(contentResolver: ContentResolver, eventId: Long) {
        // Add the notification one day before the event
        val reminderValues1 = ContentValues().apply {
            put(CalendarContract.Reminders.EVENT_ID, eventId)
            put(CalendarContract.Reminders.MINUTES, 24 * 60) // 24 hours in minutes
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues1)

        // Add the notification one hour before the event
        val reminderValues2 = ContentValues().apply {
            put(CalendarContract.Reminders.EVENT_ID, eventId)
            put(CalendarContract.Reminders.MINUTES, 60) // 1 hour in minutes
            put(CalendarContract.Reminders.METHOD, CalendarContract.Reminders.METHOD_ALERT)
        }
        contentResolver.insert(CalendarContract.Reminders.CONTENT_URI, reminderValues2)
    }

    private fun transformDateFormat(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("MMM dd, yyyy, HH:mm")
        val outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val localDateTime = LocalDateTime.parse(inputDate, inputFormatter)
        return localDateTime.format(outputFormatter)
    }
}