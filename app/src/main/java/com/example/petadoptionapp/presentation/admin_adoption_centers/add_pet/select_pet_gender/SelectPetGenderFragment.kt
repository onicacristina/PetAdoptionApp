package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender

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
import com.example.petadoptionapp.databinding.FragmentSelectPetGenderBinding
import com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender.adapter.SelectPetGenderAdapter
import com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_gender.adapter.SelectPetGenderDiffUtils
import com.example.petadoptionapp.presentation.ui.home.EPetGender
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectPetGenderFragment : BottomSheetDialogFragment(R.layout.fragment_select_pet_gender) {
    private val viewBinding: FragmentSelectPetGenderBinding by viewBinding(
        FragmentSelectPetGenderBinding::bind
    )
    private val viewModel: SelectPetGenderViewModel by viewModels()

    private lateinit var adapter: SelectPetGenderAdapter


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
        adapter = SelectPetGenderAdapter(
            SelectPetGenderDiffUtils,
            onItemClickListener = { data ->
                viewModel.setClickedGender(data)
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

    private fun setList(data: List<EPetGender>) {
        adapter.submitList(data)
    }

    private fun renderState(
        state: SelectPetGenderViewModel.State
    ) {

        fun renderListState(data: List<EPetGender>) {
            setList(data)
        }

        fun renderLoadingState() {
        }

        when (state) {
            is SelectPetGenderViewModel.State.Value -> renderListState(state.genders)
            is SelectPetGenderViewModel.State.Loading -> renderLoadingState()
        }
    }

    private fun onEvent(event: SelectPetGenderViewModel.Event) {
        when (event) {
            SelectPetGenderViewModel.Event.FINISH -> findNavController().popBackStack()
        }
    }
}