package com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours

import android.content.res.Resources
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentSelectStartHourBinding
import com.example.petadoptionapp.network.models.Hour
import com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours.adapter.SelectTimeAdapter
import com.example.petadoptionapp.presentation.admin_adoption_centers.select_available_hours.adapter.SelectTimeDiffUtils
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectStartHourFragment : BottomSheetDialogFragment(R.layout.fragment_select_start_hour) {
    private val viewBinding: FragmentSelectStartHourBinding by viewBinding(
        FragmentSelectStartHourBinding::bind
    )
    private val viewModel: SelectStartHourViewModel by viewModels()
    private lateinit var adapter: SelectTimeAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObservers()
        viewBinding.root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        val recyclerView = viewBinding.rvZoneAndDuration
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = SelectTimeAdapter(
            SelectTimeDiffUtils,
            onItemClickListener = { data ->
                viewModel.setClickedStartHour(data)
            },
        )
        recyclerView.adapter = adapter
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
                    viewModel.event.collect { value ->
                        onEvent(value)
                    }
                }
            }
        }
    }

    private fun setList(data: List<Hour>) {
        adapter.submitList(data)
    }

    private fun renderState(
        state: SelectStartHourViewModel.State
    ) {

        fun renderListState(data: List<Hour>) {
            setList(data)
        }

        fun renderLoadingState() {
        }


        when (state) {
            is SelectStartHourViewModel.State.Value -> renderListState(state.hours)
            is SelectStartHourViewModel.State.Loading -> renderLoadingState()
        }
    }

    private fun onEvent(event: SelectStartHourViewModel.Event) {
        when (event) {
            SelectStartHourViewModel.Event.FINISH -> findNavController().popBackStack()
        }
    }
}