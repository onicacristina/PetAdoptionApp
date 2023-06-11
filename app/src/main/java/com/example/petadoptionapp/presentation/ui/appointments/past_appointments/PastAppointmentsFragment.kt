package com.example.petadoptionapp.presentation.ui.appointments.past_appointments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPastAppointmentsBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesAdapter
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PastAppointmentsFragment :
    BaseViewBindingFragment<FragmentPastAppointmentsBinding>(R.layout.fragment_past_appointments) {

    override val viewBinding: FragmentPastAppointmentsBinding by viewBinding(
        FragmentPastAppointmentsBinding::bind
    )
    override val viewModel: PastAppointmentsViewModel by viewModels()
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