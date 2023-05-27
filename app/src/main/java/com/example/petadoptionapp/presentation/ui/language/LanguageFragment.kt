package com.example.petadoptionapp.presentation.ui.language

import android.os.Bundle
import android.view.View
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentLanguageBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding

class LanguageFragment :
    NoBottomNavigationFragment<FragmentLanguageBinding>(R.layout.fragment_language) {
    override val viewBinding: FragmentLanguageBinding by viewBinding(FragmentLanguageBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
    }

    private fun initViews() {

    }

    private fun initListeners() {

    }
}