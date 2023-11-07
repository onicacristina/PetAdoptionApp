package com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentUpcomingAppointmentsBinding
import com.example.petadoptionapp.network.models.Booking
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter.AppointmentsDiffUtils
import com.example.petadoptionapp.presentation.ui.appointments.upcoming_appointments.adapter.UpcomingAppointmentsAdapter
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class UpcomingAppointmentsFragment :
    BaseViewBindingFragment<FragmentUpcomingAppointmentsBinding>(R.layout.fragment_upcoming_appointments) {

    override val viewBinding: FragmentUpcomingAppointmentsBinding by viewBinding(
        FragmentUpcomingAppointmentsBinding::bind
    )
    override val viewModel: UpcomingAppointmentsViewModel by viewModels()
    private lateinit var adapter: UpcomingAppointmentsAdapter

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
        viewBinding.noDataFound.btnFindPets.setOnDebounceClickListener {
            getMainActivity()?.initNavigation()
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
        val recyclerView = viewBinding.rvUpcomingAppointments
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = UpcomingAppointmentsAdapter(
            AppointmentsDiffUtils(),
            onCancelClickListener = {
                showDeleteDialog(it)
            },
            onViewDetailsItemClickListener = { booking ->
//                val bundle = Bundle()
//                bundle.putString(Constants.ADOPTION_CENTER_ID, it.adoptionCenter.id)
//                bundle.putString(Constants.PET_ID, it.id)
//                navController.navigate(R.id.toPetDetailsFragment, bundle)
                navController.navigate(
                    R.id.appointmentDetailsFragment,
                    bundleOf(
                        Constants.APPOINTMENT_DETAILS to booking,
                    )
                )
            }
        )
        recyclerView.adapter = adapter
    }

    private fun setList(data: List<Booking>) {
        adapter.submitList(data)
    }

    private fun renderState(state: UpcomingAppointmentsViewModel.State) {
        fun renderEmptyState() {
            viewBinding.rvUpcomingAppointments.isVisible = false
            viewBinding.noDataFound.container.isVisible = true
            viewBinding.pbLoading.isVisible = false
            setList(emptyList()) // Clear the list when in empty state
        }

        fun renderListState(data: List<Booking>) {
            viewBinding.rvUpcomingAppointments.isVisible = true
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = false
            setList(data)
        }

        fun renderLoadingState() {
            viewBinding.rvUpcomingAppointments.isVisible = false
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = true
            setList(emptyList()) // Clear the list when in loading state
        }

        when (state) {
            is UpcomingAppointmentsViewModel.State.Value -> renderListState(state.appointments)
            is UpcomingAppointmentsViewModel.State.Loading -> renderLoadingState()
            else -> renderEmptyState()
        }
    }

    private fun showDeleteDialog(data: Booking) {
        showDialog(
            requireContext(),
            getString(R.string.delete_appointment),
            getString(R.string.delete_appointment_description),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            positiveAction = {
                viewModel.deleteAppointmentRequest(data)
            },
            getString(R.string.cancel),
            negativeAction = null,
        )
    }

}