package com.example.petadoptionapp.presentation.ui.home.pet_details.book_appointment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentBookAppointmentBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class BookAppointmentFragment :
    NoBottomNavigationFragment<FragmentBookAppointmentBinding>(R.layout.fragment_book_appointment) {

    override val viewBinding: FragmentBookAppointmentBinding by viewBinding(
        FragmentBookAppointmentBinding::bind
    )
    override val viewModel: BookAppointmentViewModel by viewModels()
    val calendar: Calendar = Calendar.getInstance()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = "Book Appointment"
        //desabling privious date
    // calendarView.setMinDate(System.currentTimeMillis() - 1000);
        calendar.set(Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_WEEK)
        val minDate: Long = calendar.timeInMillis
        viewBinding.calendarView.minDate = minDate

    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
    }

    private fun initObservers() {

    }
}