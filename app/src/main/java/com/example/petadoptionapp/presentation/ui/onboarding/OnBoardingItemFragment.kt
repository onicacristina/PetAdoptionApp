package com.example.petadoptionapp.presentation.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentOnboardingItemBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

const val ID = "id"

class OnBoardingItemFragment :
    BaseViewBindingFragment<FragmentOnboardingItemBinding>(R.layout.fragment_onboarding_item) {

    override val viewBinding: FragmentOnboardingItemBinding by viewBinding(
        FragmentOnboardingItemBinding::bind
    )
    private lateinit var slide: EOnBoardingSliderType


    companion object {
        fun newInstance(slide: EOnBoardingSliderType): OnBoardingItemFragment =
            OnBoardingItemFragment().apply {
                arguments = bundleOf(
                    ID to slide.ordinal
                )
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        slide = EOnBoardingSliderType.values()[arguments?.getInt(ID) ?: 0]
        initViews()
    }

    private fun initViews() {
        viewBinding.ivLogo.setImageResource(slide.logo)
        viewBinding.tvTitle.text = getString(slide.title)
        viewBinding.tvDescription.text = getString(slide.description)
    }
}