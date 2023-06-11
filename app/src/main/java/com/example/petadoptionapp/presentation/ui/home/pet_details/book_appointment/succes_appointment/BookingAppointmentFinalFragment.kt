package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment.succes_appointment

import android.os.Bundle
import android.text.SpannableString
import android.view.View
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
        viewBinding.btnGoToAppointments.setOnDebounceClickListener {
            //todo
        }
        viewBinding.btnAddToCalendar.setOnDebounceClickListener {
            //todo
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
        viewBinding.tvTime.text = state.appointmentTime
        viewBinding.tvSubtitle.addClickableLink(
            fullText = getString(R.string.waiting_for_you, state.petName),
            linkText = SpannableString(state.petName),
            context = requireContext(),
            isBolded = true,
            isUnderlined = false,
            textColor = R.color.text_black
        ) {
        }
    }
}