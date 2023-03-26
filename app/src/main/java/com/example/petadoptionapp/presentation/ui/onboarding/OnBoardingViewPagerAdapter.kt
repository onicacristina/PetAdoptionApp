package com.example.petadoptionapp.presentation.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter(fragment: OnBoardingFragment) : FragmentStateAdapter(fragment) {

    private val pagesList: List<OnBoardingItemFragment> = listOf(
        OnBoardingItemFragment.newInstance(EOnBoardingSliderType.PAGE_1),
        OnBoardingItemFragment.newInstance(EOnBoardingSliderType.PAGE_2),
        OnBoardingItemFragment.newInstance(EOnBoardingSliderType.PAGE_3)
    )

    override fun getItemCount(): Int {
        return pagesList.size
    }

    override fun createFragment(position: Int): Fragment {
        return pagesList[position]
    }

}