package com.example.petadoptionapp.presentation.ui.profile.change_password

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentChangePasswordBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ChangePasswordFragment :
    NoBottomNavigationFragment<FragmentChangePasswordBinding>(R.layout.fragment_change_password) {
    override val viewBinding: FragmentChangePasswordBinding by viewBinding(
        FragmentChangePasswordBinding::bind
    )
    override val viewModel: ChangePasswordViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.toolbar.tvTitle.text = getString(R.string.change_password)
    }

    private fun initListeners() {
        viewBinding.toolbar.ivBack.setOnClickListener {
            navController.popBackStack()
        }

        viewBinding.etCurrentPassword.doAfterTextChanged { text ->
            viewModel.onPasswordChanged(text.toString())
        }
        viewBinding.etNewPassword.doAfterTextChanged { text ->
            viewModel.onNewPasswordChanged(text.toString())
        }
        viewBinding.etConfirmNewPassword.doAfterTextChanged { text ->
            viewModel.onConfirmNewPasswordChanged(text.toString())
        }

        viewBinding.btnSave.setOnDebounceClickListener {
            viewModel.changePassword()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.state.collect { value ->
                        render(value)
                    }
                }
                launch {
                    viewModel.event.collect { value ->
                        onEvent(value)
                    }
                }
            }
        }
    }

    private fun onEvent(event: ChangePasswordViewModel.Event) {
        when (event) {
            ChangePasswordViewModel.Event.SUCCESS -> {
                hideInfoOrError()
                navController.popBackStack()
            }
            ChangePasswordViewModel.Event.SHORT_PASSWORD -> showInfoOrError(getString(R.string.password_length_rule))
            ChangePasswordViewModel.Event.NO_MATCH_PASSWORD -> showInfoOrError(getString(R.string.passwords_do_not_match))
            ChangePasswordViewModel.Event.CHANGE_FAILURE -> TODO()
            ChangePasswordViewModel.Event.PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER -> showInfoOrError(
                getString(R.string.password_rules)
            )
        }
    }

    private fun render(state: ChangePasswordViewModel.State) {
        updateSaveButton(state.isEnabledSaveButton)
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }

    private fun showInfoOrError(error: String) {
        viewBinding.clInfoOrError.isVisible = true
        viewBinding.tvInfoOrError.text = error
    }

    private fun hideInfoOrError() {
        viewBinding.clInfoOrError.isVisible = false
    }
}