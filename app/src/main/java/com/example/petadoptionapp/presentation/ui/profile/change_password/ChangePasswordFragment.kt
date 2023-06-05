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
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
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
            //todo
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

    private fun onEvent(value: ChangePasswordViewModel.Event) {

    }

    private fun render(value: ChangePasswordViewModel.State) {
        TODO("Not yet implemented")
    }

    private fun updateSaveButton(isActive: Boolean) {
        viewBinding.btnSave.isEnabled = isActive
        viewBinding.btnSave.alpha = if (isActive) 1f else .6f
    }

    private fun showInfoOrError(infoOrError: InfoOrErrorAuthentication) {
        viewBinding.clInfoOrError.isVisible = true
        viewBinding.tvInfoOrError.text = infoOrError.stringResource?.let { getString(it) }
    }

    private fun hideInfoOrError() {
        viewBinding.clInfoOrError.isVisible = false
    }
}