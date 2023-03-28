package com.example.petadoptionapp.presentation.ui.onboarding

import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentOnboardingBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment :
    BaseViewBindingFragment<FragmentOnboardingBinding>(R.layout.fragment_onboarding) {

    override val viewBinding: FragmentOnboardingBinding by viewBinding(FragmentOnboardingBinding::bind)
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: OnBoardingViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
        initViews()
        initListeners()
    }

    private fun setupViewPager() {
        adapter = OnBoardingViewPagerAdapter(this)
        val dotsIndicator = viewBinding.dotsIndicator
        viewPager = viewBinding.viewPager2
        viewPager.adapter = adapter
        dotsIndicator.attachTo(viewPager)
    }

    private fun initListeners() {
        viewBinding.btnContinueGetStarted.setOnClickListener {
            if (viewPager.currentItem == EOnBoardingSliderType.values().size - 1) {
                openSignIn()
            } else {
                goToNextPage()
            }
        }
        viewBinding.tvSkip.setOnClickListener {
            openSignIn()
        }
    }

    private fun initViews() {
        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == EOnBoardingSliderType.values().size - 1) {
                    viewBinding.tvSkip.visibility = View.GONE
                    viewBinding.tvContinueGetStarted.text = getString(R.string.btn_get_started)
                } else {
                    viewBinding.tvSkip.visibility = View.VISIBLE
                    viewBinding.tvContinueGetStarted.text = getString(R.string.btn_continue)
                }
            }
        })
    }

    private fun goToNextPage() {
        viewPager.setCurrentItem(viewPager.currentItem + 1, false)
    }

    private fun openSignIn() {
//        AppStateFlagsPrefs().tutorialShown()
//        navController.popBackStack()
//        navController.navigate(R.id.navigation_sign_in)
        navController.navigate(R.id.loginFragment)
    }

}