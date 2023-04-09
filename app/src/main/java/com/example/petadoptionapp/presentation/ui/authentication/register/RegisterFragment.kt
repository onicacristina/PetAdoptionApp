package com.example.petadoptionapp.presentation.ui.authentication.register

import android.os.Bundle
import android.text.SpannableString
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentRegisterBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.ui.authentication.InfoOrErrorAuthentication
import com.example.petadoptionapp.presentation.utils.extensions.addClickableLink
import com.example.petadoptionapp.presentation.utils.extensions.setOnDebounceClickListener
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment :
    BaseViewBindingFragment<FragmentRegisterBinding>(R.layout.fragment_register) {
    override val viewBinding: FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    override val viewModel: RegisterViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initListeners()
        initObservers()
    }

    private fun initViews() {
        viewBinding.cbTermsAndConditions.addClickableLink(
            fullText = getString(R.string.agree_to_the_terms_and_conditions),
            linkText = SpannableString(getString(R.string.terms_and_conditions)),
            context = requireContext(),
            textColor = R.color.blue_light
        ) {
            //TODO("replace this")
            Toast.makeText(activity, "t&c", Toast.LENGTH_SHORT).show()
        }
        viewBinding.goToLogin.addClickableLink(
            fullText = getString(R.string.go_to_login),
            linkText = SpannableString(getString(R.string.register_desc)),
            context = requireContext(),
            textColor = R.color.blue_light
        ) {
            navController.navigate(R.id.loginFragment)
        }
    }

    private fun initListeners() {
        viewBinding.etFirstName.doAfterTextChanged {
            viewModel.onFirstNameChanged(it.toString())
        }

        viewBinding.etLastName.doAfterTextChanged {
            viewModel.onLastNameChanged(it.toString())
        }

        viewBinding.etEmail.doAfterTextChanged {
            viewModel.onEmailChanged(it.toString())
        }

        viewBinding.etPassword.doAfterTextChanged {
            viewModel.onPasswordChanged(it.toString())
        }

        viewBinding.cbTermsAndConditions.setOnCheckedChangeListener { _, isChecked ->
            viewModel.onTermsCheckedChanged(isChecked)
        }

        initTogglePasswordMask()

        viewBinding.btnRegister.setOnDebounceClickListener {
            onRegisterPressed()
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

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.buttonState.collect { isActive ->
                        updateRegisterButton(isActive)
                    }
                }

            }
        }
    }

    private fun onRegisterPressed() {
        val someError: InfoOrErrorAuthentication = viewModel.getInputsErrors()
        when (someError) {
            InfoOrErrorAuthentication.EMAIL_INVALID -> showInfoOrError(someError)
            InfoOrErrorAuthentication.PASSWORD_LENGTH -> showInfoOrError(someError)
            InfoOrErrorAuthentication.PASSWORD_ONE_UPPERCASE_AND_ONE_NUMBER -> showInfoOrError(
                someError
            )
            InfoOrErrorAuthentication.NONE -> {
                hideInfoOrError()
                viewModel.registerUser()
            }
        }
    }

    private fun updateRegisterButton(isActive: Boolean) {
        viewBinding.btnRegister.isEnabled = isActive
    }

    private fun showInfoOrError(infoOrError: InfoOrErrorAuthentication) {
        viewBinding.clInfoOrError.isVisible = true
        viewBinding.tvInfoOrError.text = infoOrError.stringResource?.let { getString(it) }
    }

    private fun hideInfoOrError() {
        viewBinding.clInfoOrError.isVisible = false
    }

}