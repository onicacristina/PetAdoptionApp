package com.example.petadoptionapp.presentation.admin_adoption_centers.home_admin

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentHomeAdminBinding
import com.example.petadoptionapp.network.models.response.AnimalResponse
import com.example.petadoptionapp.presentation.admin_adoption_centers.home_admin.adapter.HomeAdminAdapter
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.home.PetCategoryModel
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsDiffutils
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryAdapterNew
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryDiffUtils
import com.example.petadoptionapp.presentation.utils.Constants
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeAdminFragment :
    BaseViewBindingFragment<FragmentHomeAdminBinding>(R.layout.fragment_home_admin) {
    override val viewBinding: FragmentHomeAdminBinding by viewBinding(
        FragmentHomeAdminBinding::bind
    )
    override val viewModel: HomeAdminViewModel by viewModels()
    private lateinit var petCategoryAdapter: PetCategoryAdapterNew
    private lateinit var petsAdapter: HomeAdminAdapter

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
    }

    private fun initListeners() {
        viewBinding.etSearch.doAfterTextChanged { value ->
            value ?: return@doAfterTextChanged
            viewModel.onSearchChanged(value.toString())
        }

        viewBinding.btnAdd.setOnDebounceClickListener {
            navController.navigate(R.id.addPetFragment)
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
                    viewModel.state.collect { value ->
                        renderState(value)
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
                recyclerView.scrollToPosition(it.id.toInt())
            })
        recyclerView.adapter = petCategoryAdapter
    }

    private fun setPetCategoryList(data: List<PetCategoryModel>) {
        petCategoryAdapter.submitList(data)
    }

    private fun setupPetsRecyclerView() {
        val recyclerView = viewBinding.rvPets
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        petsAdapter = HomeAdminAdapter(
            HomePetsDiffutils(),
            onItemPetClickListener = {
                val bundle = Bundle()
                bundle.putString(Constants.ADOPTION_CENTER_ID, it.adoptionCenterId)
                bundle.putString(Constants.PET_ID, it.id)
                navController.navigate(R.id.toPetDetailsFragment, bundle)
            },
            onEditClickListener = {

            },
            onDeleteClickListener = {
                showDeleteDialog()
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

    private fun renderState(state: HomeAdminViewModel.State) {
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
        }

        when (state) {
            is HomeAdminViewModel.State.Value -> renderListState(state.petsList)
            is HomeAdminViewModel.State.Loading -> renderLoadingState()
            else -> renderEmptyState()
        }
    }

    private fun showDeleteDialog() {
        showDialog(
            requireContext(),
            getString(R.string.delete_pet),
            getString(R.string.delete_pet_description),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            {
//                viewModel.deleteAccount()
            },
            getString(R.string.cancel),
            null,
        )
    }

}