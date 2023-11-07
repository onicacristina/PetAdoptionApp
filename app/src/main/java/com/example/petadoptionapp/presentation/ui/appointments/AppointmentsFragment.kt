package com.example.petadoptionapp.presentation.ui.appointments

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentAppointmentsBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.favorite.adapter.FavoritesAdapter
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppointmentsFragment :
    BaseViewBindingFragment<FragmentAppointmentsBinding>(R.layout.fragment_appointments) {

    override val viewBinding: FragmentAppointmentsBinding by viewBinding(FragmentAppointmentsBinding::bind)
    override val viewModel: AppointmentsViewModel by viewModels()
    private lateinit var adapter: FavoritesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun setupViewPagerAndTabLayout() {
        val viewPager = viewBinding.vpViewPager
        val tabLayout = viewBinding.tabLayoutFines

        val adapter = AppointmentsViewPagerAdapter(this)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getString(adapter.getTitle(position))
        }.attach()
    }

    private fun initViews() {
        initToolbar()
        setupViewPagerAndTabLayout()
    }

    private fun initToolbar() {
        viewBinding.toolbar.ivBack.isVisible = false
        viewBinding.toolbar.tvTitle.text = getString(R.string.appointments)
    }

    private fun initListeners() {

    }

    private fun initObservers() {

    }

}