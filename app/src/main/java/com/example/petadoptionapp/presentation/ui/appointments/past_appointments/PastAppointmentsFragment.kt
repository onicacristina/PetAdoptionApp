package com.example.petadoptionapp.presentation.ui.appointments.past_appointments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentPastAppointmentsBinding
import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.appointments.past_appointments.adapter.PastAppointmentsAdapter
import com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter.AppointmentsDiffUtils
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PastAppointmentsFragment :
    BaseViewBindingFragment<FragmentPastAppointmentsBinding>(R.layout.fragment_past_appointments) {

    override val viewBinding: FragmentPastAppointmentsBinding by viewBinding(
        FragmentPastAppointmentsBinding::bind
    )
    override val viewModel: PastAppointmentsViewModel by viewModels()
    private lateinit var adapter: PastAppointmentsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        setupRecyclerView()
    }

    private fun initListeners() {

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
        val recyclerView = viewBinding.rvPastAppointmnets
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = PastAppointmentsAdapter(
            AppointmentsDiffUtils()
        )
        recyclerView.adapter = adapter
    }

    private fun setList(data: List<Booking>) {
        adapter.submitList(data)
    }

    private fun renderState(state: PastAppointmentsViewModel.State) {
        fun renderEmptyState() {
            viewBinding.rvPastAppointmnets.isVisible = false
            viewBinding.noDataFound.container.isVisible = true
            viewBinding.pbLoading.isVisible = false
            setList(emptyList()) // Clear the list when in empty state
        }

        fun renderListState(data: List<Booking>) {
            viewBinding.rvPastAppointmnets.isVisible = true
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = false
            setList(data)
        }

        fun renderLoadingState() {
            viewBinding.rvPastAppointmnets.isVisible = false
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = true
            setList(emptyList()) // Clear the list when in loading state
        }

        when (state) {
            is PastAppointmentsViewModel.State.Value -> renderListState(state.appointments)
            is PastAppointmentsViewModel.State.Loading -> renderLoadingState()
            else -> renderEmptyState()
        }
    }
}