package com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentUpcomingAppointmentsBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesAdapter
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpcomingAppointmentsFragment :
    BaseViewBindingFragment<FragmentUpcomingAppointmentsBinding>(R.layout.fragment_upcoming_appointments) {

    override val viewBinding: FragmentUpcomingAppointmentsBinding by viewBinding(
        FragmentUpcomingAppointmentsBinding::bind
    )
    override val viewModel: UpcomingAppointmentsViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {

    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

}