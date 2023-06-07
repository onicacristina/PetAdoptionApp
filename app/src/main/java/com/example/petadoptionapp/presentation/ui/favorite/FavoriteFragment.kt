package com.example.petadoptionapp.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentFavoriteBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesAdapter
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesDiffUtils
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseViewBindingFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override val viewBinding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initToolbar()
        setupRecyclerView()
    }

    private fun initToolbar() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.favorites)
        viewBinding.toolbar.ivBack.isVisible = false
    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding.rvFavorites
        // setting grid layout manager to implement grid view
        // in this method '2' represents number of columns to be displayed in grid view
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = FavoritesAdapter(
            FavoritesDiffUtils(),
            onItemPetClickListener = {

            },
            onFavoriteButtonClickListener = {

            }
        )
        recyclerView.adapter = adapter
    }
}