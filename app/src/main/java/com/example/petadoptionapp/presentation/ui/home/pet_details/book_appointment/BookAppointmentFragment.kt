package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentBookAppointmentBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.adapter.AvailableAppointmentsHoursAdapter
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.adapter.AvailableAppointmentsHoursDiffUtils
import com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.model.AvailableHour
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class BookAppointmentFragment :
    NoBottomNavigationFragment<FragmentBookAppointmentBinding>(R.layout.fragment_book_appointment) {

    override val viewBinding: FragmentBookAppointmentBinding by viewBinding(
        FragmentBookAppointmentBinding::bind
    )
    override val viewModel: BookAppointmentViewModel by viewModels()
    private lateinit var adapter: AvailableAppointmentsHoursAdapter
    private val calendar: Calendar = Calendar.getInstance()
    private val args: BookAppointmentFragmentArgs by navArgs()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.book_appointment)
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_WEEK)
        val currentDate = Calendar.getInstance()

        // Set the current date as the minimum date in the CalendarView
        viewBinding.calendarView.minDate = currentDate.timeInMillis

        setupRecyclerView()
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.btnConfirm.setOnDebounceClickListener {
            viewModel.checkSelectedHour()
        }
        viewBinding.calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance().apply {
                set(year, month, dayOfMonth)
            }.time

            viewModel.onSelectedDate(selectedDate)
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        renderState(value)
                    }
                }
                launch {
                    viewModel.event.collect { event ->
                        onEvent(event)
                    }
                }
            }
        }
    }

    private fun onEvent(event: BookAppointmentViewModel.Event) {
        when (event) {
            BookAppointmentViewModel.Event.SUCCESS -> {
                openSuccessBookingAppointmentScreen(
                    petName = args.pet?.name ?: "",
                    bookingTime = viewModel.appointmentTimeFinal,
                    location = args.adoptionCenter?.getFullAddress() ?: "",
//                    petId = args.
                )
            }
            BookAppointmentViewModel.Event.FAILURE -> {}
            BookAppointmentViewModel.Event.SELECT_HOUR -> {
                showErrorPopup(getString(R.string.error_select_hour))
            }
            BookAppointmentViewModel.Event.TIME_SELECTED -> {
                viewModel.addBooking()
                Timber.e("yay booking")
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding.rvAvailableTime
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        adapter = AvailableAppointmentsHoursAdapter(
            AvailableAppointmentsHoursDiffUtils(),
            onItemClickListener = {
                viewModel.selectHour(it)
            })
        recyclerView.adapter = adapter
    }

    private fun setList(data: List<AvailableHour>) {
        adapter.submitList(data)
    }

    private fun renderState(state: BookAppointmentViewModel.State) {

        fun renderListState(data: List<AvailableHour>) {
            setList(data)
        }


        when (state) {
            is BookAppointmentViewModel.State.Value -> renderListState(state.petsList)
            is BookAppointmentViewModel.State.Loading -> {}
            else -> {}
        }
    }

    private fun openSuccessBookingAppointmentScreen(
        petName: String,
        bookingTime: String,
        location: String,
//        petId: String
    ) {
        navController.navigate(
            R.id.bookingAppointmentFinalFragment,
            bundleOf(
                Constants.PET_NAME to petName,
                Constants.BOOKING_TIME to bookingTime,
                Constants.APPOINTMENT_LOCATION to location,
//                Constants.PET_ID to petId
            )
        )
    }
}