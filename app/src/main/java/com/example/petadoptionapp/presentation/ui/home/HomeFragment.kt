package com.example.petadoptionapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentHomeBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsDiffutils
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryDiffUtils
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.extensions.withPrevious
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewBinding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var petCategoryAdapter: PetCategoryAdapter
    private lateinit var petsAdapter: HomePetsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        setupCategoryRecyclerView()
        setupPetsRecyclerView()
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.petCategoryObservable.collect { value ->
                        setPetCategoryList(value)
                    }
                }

                launch {
                    viewModel.state.withPrevious().collect { value ->
                        renderState(value.previous, value.current)
                    }
                }
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        val recyclerView = viewBinding.rvPetCategory
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        petCategoryAdapter = PetCategoryAdapter(
            PetCategoryDiffUtils(),
            onItemClickListener = {
                viewModel.selectPetCategory(it)
            })
        recyclerView.adapter = petCategoryAdapter
    }

    private fun setPetCategoryList(data: List<PetCategoryModel>) {
        petCategoryAdapter.submitList(data)
    }

    private fun setupPetsRecyclerView() {
        val recyclerView = viewBinding.rvPets
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        petsAdapter = HomePetsAdapter(
            HomePetsDiffutils(),
            onItemPetClickListener = {
                //TODO
            }
        )
        recyclerView.adapter = petsAdapter
    }

    private fun setList(data: List<AnimalResponse>) {
        petsAdapter.submitList(data)
    }

    private fun renderState(
        previous: HomeViewModel.State?,
        state: HomeViewModel.State
    ) {

        fun renderEmptyState() {
            viewBinding.rvPets.isVisible = false
            viewBinding.noDataFound.container.isVisible = true
            viewBinding.pbLoading.isVisible = false
            setList(emptyList())
        }

        fun renderListState(data: List<AnimalResponse>) {
            viewBinding.rvPets.isVisible = true
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = false
            setList(data)
        }

        fun renderLoadingState() {
            viewBinding.rvPets.isVisible = false
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = true
        }

        if (previous?.javaClass != state.javaClass) {
            TransitionManager.beginDelayedTransition(viewBinding.root)
        }

        when (state) {
            is HomeViewModel.State.Value -> renderListState(state.petsList)
            is HomeViewModel.State.Loading -> renderLoadingState()
            else -> renderEmptyState()
        }
    }
}