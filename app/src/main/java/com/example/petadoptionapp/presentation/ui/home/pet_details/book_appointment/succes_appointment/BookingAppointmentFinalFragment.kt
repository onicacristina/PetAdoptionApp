package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.succes_appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentBookingAppointmentFinalBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

class BookingAppointmentFinalFragment :
    NoBottomNavigationFragment<FragmentBookingAppointmentFinalBinding>(R.layout.fragment_booking_appointment_final) {
    override val viewBinding: FragmentBookingAppointmentFinalBinding by viewBinding(
        FragmentBookingAppointmentFinalBinding::bind
    )
    override val viewModel: BookingAppointmentFinalViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservables()
    }

    private fun initViews() {

    }

    private fun initListeners() {

    }

    private fun initObservables() {

    }
}