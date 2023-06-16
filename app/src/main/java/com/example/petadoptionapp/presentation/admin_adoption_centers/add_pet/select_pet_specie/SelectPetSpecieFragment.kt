package com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie

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
import com.example.petadoptionapp.databinding.FragmentSelectPetSpecieBinding
import com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie.adapter.SelectPetSpecieAdapter
import com.example.petadoptionapp.presentation.admin_adoption_centers.add_pet.select_pet_specie.adapter.SelectPetSpecieDiffUtils
import com.example.petadoptionapp.presentation.ui.home.EPetCategory
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SelectPetSpecieFragment : BottomSheetDialogFragment(R.layout.fragment_select_pet_specie) {
    private val viewBinding: FragmentSelectPetSpecieBinding by viewBinding(
        FragmentSelectPetSpecieBinding::bind
    )
    private val viewModel: SelectPetSpecieViewModel by viewModels()

    private lateinit var adapter: SelectPetSpecieAdapter


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
        adapter = SelectPetSpecieAdapter(
            SelectPetSpecieDiffUtils,
            onItemClickListener = { data ->
                viewModel.setClickedSpecie(data)
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

    private fun setList(data: List<EPetCategory>) {
        adapter.submitList(data)
    }

    private fun renderState(
        state: SelectPetSpecieViewModel.State
    ) {

        fun renderListState(data: List<EPetCategory>) {
            setList(data)
        }

        fun renderLoadingState() {
        }

        when (state) {
            is SelectPetSpecieViewModel.State.Value -> renderListState(state.species)
            is SelectPetSpecieViewModel.State.Loading -> renderLoadingState()
        }
    }

    private fun onEvent(event: SelectPetSpecieViewModel.Event) {
        when (event) {
            SelectPetSpecieViewModel.Event.FINISH -> findNavController().popBackStack()
        }
    }
}