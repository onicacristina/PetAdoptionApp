package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import java.util.*

@AndroidEntryPoint
class BookAppointmentFragment :
    NoBottomNavigationFragment<FragmentBookAppointmentBinding>(R.layout.fragment_book_appointment) {

    override val viewBinding: FragmentBookAppointmentBinding by viewBinding(
        FragmentBookAppointmentBinding::bind
    )
    override val viewModel: BookAppointmentViewModel by viewModels()
    private lateinit var adapter: AvailableAppointmentsHoursAdapter
    val calendar: Calendar = Calendar.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.book_appointment)
        //desabling privious date
        // calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_WEEK)
        val minDate: Long = calendar.timeInMillis
        viewBinding.calendarView.minDate = minDate

        setupRecyclerView()
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.btnConfirm.setOnDebounceClickListener {
            openSuccessBookingAppointmentScreen("name", "time")
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
//                viewModel.selectPetCategory(it)
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

    private fun openSuccessBookingAppointmentScreen(petName: String, bookingTime: String) {
        navController.navigate(
            R.id.bookingAppointmentFinalFragment,
            bundleOf(Constants.PET_NAME to petName, Constants.BOOKING_TIME  to bookingTime)
        )
    }
}