package com.example.petadoptionapp.presentation.ui.favorite

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentFavoriteBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseViewBindingFragment<FragmentFavoriteBinding>(R.layout.fragment_favorite) {

    override val viewBinding: FragmentFavoriteBinding by viewBinding(FragmentFavoriteBinding::bind)
    override val viewModel: FavoriteViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        initToolbar()
    }

    private fun initToolbar() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.favorites)
        viewBinding.toolbar.ivBack.isVisible = false
    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

}