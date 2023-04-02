package com.example.petadoptionapp.presentation.ui.authentication.register

import android.os.Bundle
import android.text.SpannableString
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.example.petadoptionapp.R
import com.example.petadoptionapp.databinding.FragmentRegisterBinding
import com.example.petadoptionapp.presentation.base.BaseViewBindingFragment
import com.example.petadoptionapp.presentation.utils.extensions.addClickableLink
import com.example.petadoptionapp.presentation.utils.extensions.viewBinding
import dagger.hilt.android.AndroidEntryPoint

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
//            TODO("replace this")
            Toast.makeText(activity, "t&c", Toast.LENGTH_SHORT).show()
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
    }

    private fun initObservers() {
    }



}