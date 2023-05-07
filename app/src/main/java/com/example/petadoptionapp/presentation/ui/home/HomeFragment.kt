package com.example.petadoptionapp.presentation.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentHomeBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.home.adapter.HomePetsAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryAdapter
import com.example.petadoptionapp.presentation.ui.home.adapter.PetCategoryDiffUtils
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseViewBindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override val viewBinding: FragmentHomeBinding by viewBinding(FragmentHomeBinding::bind)
    override val viewModel: HomeViewModel by viewModels()
    private lateinit var petCategoryAdapter: PetCategoryAdapter
    private lateinit var petAdapter: HomePetsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        setupCategoryRecyclerView()
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
//                    viewModel.state.withPrevious().collect { value ->
//                        renderState(value.previous, value.current)
//                    }
                }
            }
        }
    }

    private fun setupCategoryRecyclerView() {
        val recyclerView = viewBinding.rvPetCategory
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
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
}