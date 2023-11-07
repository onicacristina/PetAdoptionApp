package com.example.petadoptionapp.presentation.ui.profile.settings

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.BuildConfig
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentSettingsBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.ProfilePrefs
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import com.example.petadoptionapp.presentation.utils.showDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SettingsFragment :
    NoBottomNavigationFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    override val viewBinding: FragmentSettingsBinding by viewBinding(
        FragmentSettingsBinding::bind
    )
    override val viewModel: SettingsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        getVersionNumber()
        initSettingsOptions()
        initToolbar()
    }

    private fun initToolbar() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.settings)
    }

    private fun initSettingsOptions() {
        viewBinding.viewLanguage.tvOption.text = getString(R.string.language)
        viewBinding.viewLanguage.ivIcon.setImageResource(R.drawable.ic_language)
        viewBinding.viewTerms.tvOption.text = getString(R.string.terms_and_conditions)
        viewBinding.viewTerms.ivIcon.setImageResource(R.drawable.ic_terms)
        viewBinding.viewDeleteAccount.tvOption.text = getString(R.string.delete_account)
        viewBinding.viewDeleteAccount.ivIcon.setImageResource(R.drawable.ic_delete)
        initSwitch()
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnDebounceClickListener {
            navController.popBackStack()
        }
        viewBinding.viewLanguage.container.setOnDebounceClickListener {
            navController.navigate(R.id.action_settingsFragment_to_languageSettingsFragment)
        }
        viewBinding.viewTerms.container.setOnDebounceClickListener {
            navController.navigate(R.id.action_settingsFragment_to_termsAndConditionsFragment)
        }
        viewBinding.viewDeleteAccount.container.setOnDebounceClickListener {
            showDeleteDialog()
        }
        initSwitchListener()
    }

    private fun initSwitchListener() {
        viewBinding.viewDarkMode.btnSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                ProfilePrefs().setAppTheme(EAppTheme.DARK)
            } else {
                ProfilePrefs().setAppTheme(EAppTheme.LIGHT)
            }
            getMainActivity()?.setAppMode()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.event.collect { value ->
                        onEvent(value)
                    }
                }
            }
        }
    }

    private fun onEvent(event: SettingsViewModel.Event) {
        when(event) {
            SettingsViewModel.Event.SUCCESS -> {
                ProfilePrefs().clearUserProfile()
                getMainActivity()?.initNavigation()
            }
        }
    }

    private fun initSwitch() {
        val appTheme = ProfilePrefs().getAppTheme()
        viewBinding.viewDarkMode.btnSwitch.isChecked = EAppTheme.LIGHT != appTheme
    }


    @SuppressLint("SetTextI18n")
    private fun getVersionNumber() {
        viewBinding.tvVersion.text = getString(R.string.version, BuildConfig.VERSION_NAME)
    }

    private fun showDeleteDialog() {
        showDialog(
            requireContext(),
            getString(R.string.delete_account),
            getString(R.string.delete_account_subtitle),
            R.drawable.btn_rounded_red,
            getString(R.string.delete),
            {
                viewModel.deleteAccount()
            },
            getString(R.string.cancel),
            null,
        )
    }
}