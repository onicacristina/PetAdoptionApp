package com.example.petadoptionapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentHomeBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsDiffutils
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryAdapterNew
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryDiffUtils
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.extensions.withPrevious
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewBinding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var petCategoryAdapter: PetCategoryAdapterNew
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
        initPullToRefresh()
        viewBinding.title.text = getString(
            R.string.hello_user,
            ProfilePrefs().getProfile()?.firstName ?: ""
        )
    }

    private fun initListeners() {
        viewBinding.etSearch.doAfterTextChanged { value ->
            value ?: return@doAfterTextChanged
            viewModel.onSearchChanged(value.toString())
        }
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
        petCategoryAdapter = PetCategoryAdapterNew(
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
        // setting grid layout manager to implement grid view
        // in this method '2' represents number of columns to be displayed in grid view
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        petsAdapter = HomePetsAdapter(
            HomePetsDiffutils(),
            onItemPetClickListener = {
                val bundle = Bundle()
                bundle.putString(Constants.ADOPTION_CENTER_ID, it.adoptionCenterId)
                bundle.putString(Constants.PET_ID, it.id)
                navController.navigate(R.id.toPetDetailsFragment, bundle)
            }
        )
        recyclerView.adapter = petsAdapter
    }

    private fun setList(data: List<AnimalResponse>) {
        petsAdapter.submitList(data)
        viewBinding.swipeRefreshLayout.isRefreshing = false
    }

    private fun initPullToRefresh() {
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
            viewBinding.swipeRefreshLayout.isRefreshing =
                false// TODO after remove dummy data set it true
        }
        viewBinding.swipeRefreshLayout.setColorSchemeResources(
            R.color.black,
            R.color.black,
            R.color.black,
            R.color.black
        )
    }

//    private fun renderState(
//        previous: HomeViewModel.State?,
//        state: HomeViewModel.State
//    ) {
//
//        fun renderEmptyState() {
//            viewBinding.rvPets.isVisible = false
//            viewBinding.noDataFound.container.isVisible = true
//            viewBinding.pbLoading.isVisible = false
//            viewBinding.swipeRefreshLayout.isVisible = true
//            viewBinding.swipeRefreshLayout.isRefreshing = true
//            setList(emptyList())
//        }
//
//        fun renderListState(data: List<AnimalResponse>) {
//            viewBinding.rvPets.isVisible = true
//            viewBinding.noDataFound.container.isVisible = false
//            viewBinding.pbLoading.isVisible = false
//            viewBinding.swipeRefreshLayout.isVisible = true
//            viewBinding.swipeRefreshLayout.isRefreshing = true
//            setList(data)
//        }
//
//        fun renderLoadingState() {
//            viewBinding.rvPets.isVisible = false
//            viewBinding.noDataFound.container.isVisible = false
//            viewBinding.pbLoading.isVisible = true
//            viewBinding.swipeRefreshLayout.isVisible = false
//            viewBinding.swipeRefreshLayout.isRefreshing = false
//        }
//
//        if (previous?.javaClass != state.javaClass) {
//            TransitionManager.beginDelayedTransition(viewBinding.root)
//        }
//
//        when (state) {
//            is HomeViewModel.State.Value -> renderListState(state.petsList)
//            is HomeViewModel.State.Loading -> renderLoadingState()
//            else -> renderEmptyState()
//        }
//    }

    private fun renderState(previous: HomeViewModel.State?, state: HomeViewModel.State) {
        fun renderEmptyState() {
            viewBinding.rvPets.isVisible = false
            viewBinding.noDataFound.container.isVisible = true
            viewBinding.pbLoading.isVisible = false
            viewBinding.swipeRefreshLayout.isRefreshing = false
            setList(emptyList()) // Clear the list when in empty state
        }

        fun renderListState(data: List<AnimalResponse>) {
            viewBinding.rvPets.isVisible = true
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = false
            viewBinding.swipeRefreshLayout.isRefreshing = false
            setList(data)
        }

        fun renderLoadingState() {
            viewBinding.rvPets.isVisible = false
            viewBinding.noDataFound.container.isVisible = false
            viewBinding.pbLoading.isVisible = true
            viewBinding.swipeRefreshLayout.isRefreshing = false
            setList(emptyList()) // Clear the list when in loading state
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