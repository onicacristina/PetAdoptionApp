package com.example.petadoptionapp.presentation.ui.profile.settings.language_settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentLanguageSettingsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.language.ELanguage
import com.example.petadoptionapp.presentation.ui.profile.settings.language_settings.adapter.LanguageAdapter
import com.example.petadoptionapp.presentation.utils.LocaleHelper
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LanguageSettingsFragment :
    NoBottomNavigationFragment<FragmentLanguageSettingsBinding>(R.layout.fragment_language_settings) {
    override val viewBinding: FragmentLanguageSettingsBinding by viewBinding(
        FragmentLanguageSettingsBinding::bind
    )
    override val viewModel: LanguageSettingsViewModel by viewModels()
    private lateinit var adapter: LanguageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.language_settings)
        setupRecyclerView()
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.btnSave.setOnDebounceClickListener {
            selectLanguage(viewModel.languageSelected)
            navController.popBackStack()
        }
    }

    private fun selectLanguage(language: ELanguage) {
        LocaleHelper.setLocale(language)
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.languageObservable.collect { languages ->
                        setList(languages)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = viewBinding.rvLanguages
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = LanguageAdapter(
            onItemClickListener = {
                viewModel.selectLanguage(it)
            })
        recyclerView.adapter = adapter
    }

    private fun setList(data: List<Language>) {
        adapter.submitList(data)
    }
}