package com.example.petadoptionapp.presentation.ui.authentication.login

import android.os.Bundle
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentLoginBinding
import com.example.petadoptionapp.presentation.base.NoBottomNavigationFragment
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
import com.example.petadoptionapp.presentation.utils.extensions.addClickableLink
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : NoBottomNavigationFragment<FragmentLoginBinding>(R.layout.fragment_login) {
    override val viewBinding: FragmentLoginBinding by viewBinding(FragmentLoginBinding::bind)
    override val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.goToLogin.addClickableLink(
            fullText = getString(R.string.go_to_register),
            linkText = SpannableString(getString(R.string.log_in)),
            context = requireContext(),
            textColor = R.color.blue_light
        ) {
            navController.navigate(R.id.registerFragment)
        }
    }

    private fun initListeners() {
        viewBinding.etEmail.doAfterTextChanged {
            viewModel.onEmailChanged(it.toString())
        }

        viewBinding.etPassword.doAfterTextChanged {
            viewModel.onPasswordChanged(it.toString())
        }
        initTogglePasswordMask()

        viewBinding.btnLogin.setOnDebounceClickListener {
            onLoginPressed()
        }
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.buttonState.collect { isActive ->
                        updateLoginButton(isActive)
                    }
                }

            }
        }
    }

    private fun initTogglePasswordMask() {
        viewBinding.btnTogglePasswordMask.setOnClickListener {
            togglePasswordMask(
                viewBinding.etPassword,
                viewBinding.btnTogglePasswordMask,
                !viewModel.isPasswordVisible
            )
        }
    }

    private fun togglePasswordMask(editText: EditText, imageButton: ImageButton, show: Boolean) {
        editText.transformationMethod =
            if (show) HideReturnsTransformationMethod.getInstance() else PasswordTransformationMethod.getInstance()
        imageButton.setImageResource(if (show) R.drawable.ic_eye_open else R.drawable.ic_eye_closed)
        viewModel.isPasswordVisible = show
        editText.setSelection(editText.length())
    }

    private fun onLoginPressed() {
        val someError: InfoOrErrorAuthentication = viewModel.getInputsErrors()
        when (someError) {
            InfoOrErrorAuthentication.EMAIL_INVALID -> showInfoOrError(someError)
            InfoOrErrorAuthentication.NONE -> {
                hideInfoOrError()
                viewModel.loginUser()
            }
            else -> hideInfoOrError()
        }
    }

    private fun updateLoginButton(isActive: Boolean) {
        viewBinding.btnLogin.isEnabled = isActive
    }

    private fun showInfoOrError(infoOrError: InfoOrErrorAuthentication) {
        viewBinding.clInfoOrError.isVisible = true
        viewBinding.tvInfoOrError.text = infoOrError.stringResource?.let { getString(it) }
    }

    private fun hideInfoOrError() {
        viewBinding.clInfoOrError.isVisible = false
    }

}